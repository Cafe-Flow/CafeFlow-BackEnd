package org.example.cafeflow.Member.service;

import org.example.cafeflow.Member.domain.City;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.domain.State;
import org.example.cafeflow.Member.dto.MemberDto;
import org.example.cafeflow.Member.dto.MemberLoginDto;
import org.example.cafeflow.Member.dto.MemberRegistrationDto;
import org.example.cafeflow.Member.dto.TokenDto;
import org.example.cafeflow.Member.repository.CityRepository;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.Member.repository.StateRepository;
import org.example.cafeflow.Member.util.JwtTokenProvider;
import org.example.cafeflow.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public MemberDto registerMember(MemberRegistrationDto registrationDto) {
        validateRegistration(registrationDto);

        State state = getState(registrationDto.getStateId());
        City city = getCity(registrationDto.getCityId());

        Member member = buildMember(registrationDto, state, city);
        memberRepository.save(member);
        return convertToMemberDto(member);
    }

    private void validateRegistration(MemberRegistrationDto dto) {
        memberRepository.findByLoginId(dto.getLoginId()).ifPresent(m -> {
            throw new DuplicateLoginIdException("이미 등록된 ID 입니다.");
        });
        memberRepository.findByEmail(dto.getEmail()).ifPresent(m -> {
            throw new DuplicateEmailException("이미 등록된 이메일입니다.");
        });
        memberRepository.findByNickname(dto.getNickname()).ifPresent(m -> {
            throw new DuplicateNicknameException("이미 등록된 닉네임입니다.");
        });
    }

    private State getState(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID를 가진 '도/시'가 없습니다: " + id));
    }

    private City getCity(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID를 가진 '시/군,구'가 없습니다: " + id));
    }

    private Member buildMember(MemberRegistrationDto dto, State state, City city) {
        return Member.builder()
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .loginId(dto.getLoginId())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .gender(dto.getGender())
                .age(dto.getAge())
                .userType(dto.getUserType())
                .state(state)
                .city(city)
                .build();
    }

    public TokenDto loginMember(MemberLoginDto loginDto) {
        Member member = memberRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));
        validatePassword(loginDto.getPassword(), member.getPasswordHash());
        return new TokenDto(createToken(member));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }

    private String createToken(Member member) {
        return jwtTokenProvider.createToken(member.getLoginId(), member.getUserType());
    }

    public MemberDto getMemberDetailsByUsername(String username) {
        return memberRepository.findByLoginId(username)
                .map(this::convertToMemberDto)
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));
    }

    public MemberDto updateMember(Long id, MemberRegistrationDto registrationDto, String username) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("회원을 찾을 수 없습니다: " + id));
        if (!member.getLoginId().equals(username) && !member.getUserType().equals(Member.UserType.ADMIN)) {
            throw new UnauthorizedAccessException("수정 권한이 없습니다.");
        }
        updateMemberDetails(member, registrationDto);
        memberRepository.save(member);
        return convertToMemberDto(member);
    }

    private void updateMemberDetails(Member member, MemberRegistrationDto dto) {
        member.setUsername(dto.getUsername());
        member.setNickname(dto.getNickname());
        member.setEmail(dto.getEmail());
        member.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        member.setGender(dto.getGender());
        member.setAge(dto.getAge());
        member.setUserType(dto.getUserType());
        member.setCity(getCity(dto.getCityId()));
        member.setState(getState(dto.getStateId()));
    }

    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::convertToMemberDto)
                .collect(Collectors.toList());
    }

    public List<MemberDto> getMembersByNickname(String nickname) {
        return memberRepository.findByNicknameContaining(nickname).stream()
                .map(this::convertToMemberDto)
                .collect(Collectors.toList());
    }

    private MemberDto convertToMemberDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                member.getGender(),
                member.getAge(),
                member.getCity().getId(),
                member.getState().getId(),
                member.getUserType()
        );
    }

    public boolean checkIfUserIsAdmin(String token) {
        Member.UserType userType = jwtTokenProvider.getUserTypeFromToken(token);
        return userType == Member.UserType.ADMIN;
    }

    public void deleteMember(Long id, String username) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("회원을 찾을 수 없습니다: " + id));
        if (!member.getLoginId().equals(username) && !member.getUserType().equals(Member.UserType.ADMIN)) {
            throw new UnauthorizedAccessException("삭제 권한이 없습니다.");
        }
        memberRepository.deleteById(id);
    }

}
