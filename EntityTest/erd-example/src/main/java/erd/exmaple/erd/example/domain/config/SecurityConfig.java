package erd.exmaple.erd.example.domain.config;

import erd.exmaple.erd.example.domain.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 웹 보안 설정을 활성화
public class SecurityConfig {

    @Autowired
    private UserDetailsService customUserDetailsService; // 사용자 세부 정보를 로드하기 위한 서비스

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // JWT 요청 필터



    @Bean // 스프링 컨테이너에 SecurityFilterChain 빈을 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // HttpSecurity 객체를 사용하여 보안 설정을 구성
        http
                .authorizeHttpRequests((auth) -> auth // HTTP 요청에 대한 접근 권한 설정
                        .requestMatchers("/", "/api/users/**","/api/users/join").permitAll() // "/" 및 "/api/users/**" 경로는 모든 사용자에게 허용합
                        .requestMatchers( "/swagger-ui.html", "/swagger-ui/**","/v3/api-docs/**").permitAll()//스웨거 설정
                        .requestMatchers("/auth").permitAll()
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .cors(cors ->cors.disable())  // CORS 비활성화
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 관리 설정을 STATELESS(무상태)로 지정합니다.

        return http.build(); // 설정된 HttpSecurity 객체를 기반으로 SecurityFilterChain 객체를 생성하고 반환합니다.
    }

    @Bean // 스프링 컨테이너에 PasswordEncoder 빈을 등록합니다.
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화에 사용할 PasswordEncoder 객체를 생성하고 반환합니다.
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder를 사용하여 비밀번호를 암호화합니다.
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}




