package erd.exmaple.erd.example.domain.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception {

        try {//전화번호와 비밀번호로 인증 시도
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getPhoneNumber(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {//인증실패 예외 메시지
            throw new Exception("전화번호(아이디) 또는 비밀번호가 일치하지 않습니다.", e);
        }
        // 인증된 사용자 정보 로드
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getPhoneNumber());
        //jwt토큰 생성
        final String jwt = jwtUtil.generateToken(userDetails);
        //jwt 반환
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}