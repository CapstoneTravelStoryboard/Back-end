package com.example.travel_sculptor.controller;

import com.example.travel_sculptor.domain.Member;
import com.example.travel_sculptor.dto.member.LoginResponse;
import com.example.travel_sculptor.dto.member.MemberLoginRequest;
import com.example.travel_sculptor.dto.member.MemberResponse;
import com.example.travel_sculptor.dto.member.MemberSignupRequest;
import com.example.travel_sculptor.repository.MemberRepository;
import com.example.travel_sculptor.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 테스트용 -> 지워야함
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid MemberSignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid MemberLoginRequest request) {
        LoginResponse response = memberService.login(request);
        return ResponseEntity.ok(response);
    }

    // 인증이 필요한 엔드포인트 예시
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return ResponseEntity.ok(MemberResponse.of(member));
    }
}
