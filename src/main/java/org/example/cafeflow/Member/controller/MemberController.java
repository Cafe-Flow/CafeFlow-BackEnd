package org.example.cafeflow.Member.controller;

import jakarta.validation.Valid;
import org.example.cafeflow.Member.dto.MemberDto;
import org.example.cafeflow.Member.dto.MemberLoginDto;
import org.example.cafeflow.Member.dto.MemberRegistrationDto;
import org.example.cafeflow.Member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@Valid @RequestBody MemberRegistrationDto registrationDto) {
        return ResponseEntity.ok(memberService.registerMember(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberLoginDto loginDto) {
        return ResponseEntity.ok(memberService.loginMember(loginDto));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증된 사용자가 아닙니다.");
        }
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        MemberDto memberDto = memberService.getMemberDetailsByUsername(username);
        return ResponseEntity.ok(memberDto);
    }
}
