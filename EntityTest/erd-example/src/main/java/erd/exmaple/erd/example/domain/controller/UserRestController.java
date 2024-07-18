package erd.exmaple.erd.example.domain.controller;

import erd.exmaple.erd.example.domain.dto.PasswordResetRequestDTO;
import erd.exmaple.erd.example.domain.dto.UserRequestDTO;
import erd.exmaple.erd.example.domain.dto.UserResponseDTO;
import erd.exmaple.erd.example.domain.dto.UserPhoneNumberCheckResultDTO;
import erd.exmaple.erd.example.domain.jwt.AuthRequest;
import erd.exmaple.erd.example.domain.jwt.AuthResponse;
import erd.exmaple.erd.example.domain.jwt.JwtUtil;
import erd.exmaple.erd.example.domain.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/join")
    public ResponseEntity<UserResponseDTO.JoinResultDTO> joinUser(@RequestBody @Valid UserRequestDTO.JoinDto joinDto) {
        UserResponseDTO.JoinResultDTO response = userService.joinUser(joinDto);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-phoneNumber")
    public ResponseEntity<UserPhoneNumberCheckResultDTO> checkPhoneNumber(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.checkPhoneNumber(phoneNumber));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        UserResponseDTO user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login") // 로그인 엔드포인트
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // 사용자 인증 시도
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getPhoneNumber(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // 인증 실패 시 예외 처리
            throw new Exception("Incorrect phone number or password", e);
        }

        // 인증된 사용자 정보 로드
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getPhoneNumber());

        // JWT 토큰 생성
        final String jwt = jwtUtil.generateToken(userDetails);

        // 생성된 JWT 토큰을 응답으로 반환
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequestDTO request) {
        String phoneNumber = request.getPhoneNumber();
        String confirmPhoneNumber = request.getConfirmPhoneNumber();

        if (!phoneNumber.equals(confirmPhoneNumber)) {
            return ResponseEntity.badRequest().body("Phone numbers do not match.");
        }

        String newPassword = userService.resetPasswordByPhoneNumber(phoneNumber);
        if (newPassword != null) {
            return ResponseEntity.ok("Password reset successfully. Your new password is: " + newPassword);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reset password.");
        }
    }
}