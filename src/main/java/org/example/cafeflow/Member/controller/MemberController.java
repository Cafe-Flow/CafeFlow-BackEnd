package org.example.cafeflow.Member.controller;

import jakarta.validation.Valid;
import org.example.cafeflow.Member.dto.MemberDto;
import org.example.cafeflow.Member.dto.MemberLoginDto;
import org.example.cafeflow.Member.dto.MemberRegistrationDto;
import org.example.cafeflow.Member.dto.TokenDto;
import org.example.cafeflow.Member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> registerMember(@Valid @RequestBody MemberRegistrationDto registrationDto) {
        TokenDto tokenDto = memberService.registerMember(registrationDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberLoginDto loginDto) {
        return ResponseEntity.ok(memberService.loginMember(loginDto));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MemberDto memberDto = memberService.getMemberDetailsByUsername(userDetails.getUsername());
        return ResponseEntity.ok(memberDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable("id") Long id, @Valid @RequestBody MemberRegistrationDto registrationDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MemberDto updatedMember = memberService.updateMember(id, registrationDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        memberService.deleteMember(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        List<MemberDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/members/search/by-nickname")
    public ResponseEntity<List<MemberDto>> getMembersByNickname(@RequestParam("nickname") String nickname) {
        List<MemberDto> members = memberService.getMembersByNickname(nickname);
        return ResponseEntity.ok(members);
    }
    @GetMapping("/check-admin")
    public ResponseEntity<String> checkIfUserIsAdmin(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "").trim();
        boolean isAdmin = memberService.checkIfUserIsAdmin(token);
        if (isAdmin) {
            return ResponseEntity.ok("관리자입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자만 접근 가능합니다.");
        }
    }
}
