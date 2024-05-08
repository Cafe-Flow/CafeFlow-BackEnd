package org.example.cafeflow.Member.service;

import org.example.cafeflow.Member.domain.City;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.domain.State;
import org.example.cafeflow.Member.dto.*;
import org.example.cafeflow.Member.repository.CityRepository;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.Member.repository.StateRepository;
import org.example.cafeflow.Member.util.JwtTokenProvider;
import org.example.cafeflow.cafe.repository.CafeRepository;
import org.example.cafeflow.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    @Autowired
    private CafeRepository cafeRepository;

    public TokenDto registerMember(MemberRegistrationDto registrationDto) {
        validateRegistration(registrationDto);

        State state = getState(registrationDto.getStateId());
        City city = getCity(registrationDto.getCityId());

        Member member = buildMember(registrationDto, state, city);
        memberRepository.save(member);
        String token = jwtTokenProvider.createToken(member.getLoginId(), member.getUserType());
        return new TokenDto(token);
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
        byte[] imageBytes = null;
        try {
            imageBytes = dto.getImage() != null ? dto.getImage().getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

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
                .image(imageBytes)
                .build();
    }


    public TokenDto loginMember(MemberLoginDto loginDto) {
        Member member = memberRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));
        validatePassword(loginDto.getPassword(), member.getPasswordHash());
        String token = jwtTokenProvider.createToken(member.getLoginId(), member.getUserType());
        return new TokenDto(token);
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

    public MemberDto updateMemberDetails(Long id, MemberUpdateDto updateDto, String username) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 회원을 찾을 수 없습니다: " + id));

        if (!existingMember.getLoginId().equals(username) && !existingMember.getUserType().equals(Member.UserType.ADMIN)) {
            throw new UnauthorizedAccessException("이 회원 정보를 업데이트할 권한이 없습니다.");
        }

        updateMemberFromDto(existingMember, updateDto);
        memberRepository.save(existingMember);
        return convertToMemberDto(existingMember);
    }

    private void updateMemberFromDto(Member member, MemberUpdateDto dto) {
        byte[] imageBytes = null;
        try {
            imageBytes = dto.getImage() != null ? dto.getImage().getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("이미지 변환 중 오류가 발생했습니다.", e);
        }

        member.setUsername(dto.getUsername());
        member.setNickname(dto.getNickname());
        member.setEmail(dto.getEmail());
        member.setGender(dto.getGender());
        member.setAge(dto.getAge());
        member.setUserType(dto.getUserType());
        member.setCity(getCity(dto.getCityId()));
        member.setState(getState(dto.getStateId()));
        member.setImage(imageBytes);
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
                member.getUserType(),
                member.getImage()
        );
    }

    public boolean checkIfUserIsAdmin(String token) {
        Member.UserType userType = jwtTokenProvider.getUserTypeFromToken(token);
        return userType == Member.UserType.ADMIN;
    }
    //--형준 수정--//
    @Transactional
    public void deleteMember(Long id, String username) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("회원을 찾을 수 없습니다: " + id));
        if (!member.getLoginId().equals(username) && !member.getUserType().equals(Member.UserType.ADMIN)) {
            throw new UnauthorizedAccessException("삭제 권한이 없습니다.");
        }
        List<Long> cafeIdList = cafeRepository.findByUserId(id);
        for (Long i : cafeIdList) {
            cafeRepository.delete(i);
        }
        memberRepository.deleteById(id);
    }

    public void changeMemberPassword(Long memberId, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 회원을 찾을 수 없습니다: " + memberId));

        if (!passwordEncoder.matches(currentPassword, member.getPasswordHash())) {
            throw new InvalidPasswordException("현재 비밀번호가 정확하지 않습니다.");
        }

        member.setPasswordHash(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

}
