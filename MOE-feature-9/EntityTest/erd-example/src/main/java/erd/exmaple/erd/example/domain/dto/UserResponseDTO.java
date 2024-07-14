package erd.exmaple.erd.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        Long user_id; // 사용자 ID
        LocalDateTime created_at; // 생성 시간
        boolean isSuccess; // 성공 여부
        String message; // 응답 메시지
    }
}