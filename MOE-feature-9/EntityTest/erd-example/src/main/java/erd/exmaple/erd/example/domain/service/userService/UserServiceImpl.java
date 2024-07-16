package erd.exmaple.erd.example.domain.service.userService;


import erd.exmaple.erd.example.domain.UserEntity;
import erd.exmaple.erd.example.domain.converter.UserConverter;
import erd.exmaple.erd.example.domain.repository.UserRepository;
import erd.exmaple.erd.example.domain.dto.UserRequestDTO;
import erd.exmaple.erd.example.domain.dto.UserResponseDTO;
import erd.exmaple.erd.example.domain.dto.NicknameCheckResultDTO;
import erd.exmaple.erd.example.domain.enums.Ad;
import erd.exmaple.erd.example.domain.enums.LoginStatus;
import erd.exmaple.erd.example.domain.enums.Marketing;
import erd.exmaple.erd.example.domain.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserResponseDTO.JoinResultDTO joinUser(UserRequestDTO.JoinDto joinDto) {
        // 비밀번호 확인
        if (!joinDto.getPassword().equals(joinDto.getConfirmPassword())) {
            return UserResponseDTO.JoinResultDTO.builder()
                    .isSuccess(false)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
        }

        // 닉네임(아이디) 중복 확인
        if (userRepository.findByNickname(joinDto.getNickname()).isPresent()) {
            return UserResponseDTO.JoinResultDTO.builder()
                    .isSuccess(false)
                    .message("닉네임이 이미 사용 중입니다.")
                    .build();
        }

        // UserEntity 생성
        UserEntity newUser = UserEntity.builder()
                .password(joinDto.getPassword())
                .phoneNumber(joinDto.getPhoneNumber())
                .nickname(joinDto.getNickname())
                .status(LoginStatus.ACTIVE)
                .ad(Ad.ACTIVE)
                .marketing(Marketing.ACTIVE)
                .build();

        // 사용자 저장
        UserEntity savedUser = userRepository.save(newUser);

        // 응답 DTO 생성
        return UserResponseDTO.JoinResultDTO.builder()
                .user_id(savedUser.getId())
                .created_at(LocalDateTime.now())
                .isSuccess(true)
                .message("회원가입이 성공적으로 완료되었습니다.")
                .build();
    }

    @Override
    public NicknameCheckResultDTO checkNickname(String nickname) {
        boolean isNicknameTaken = userRepository.findByNickname(nickname).isPresent();
        return new NicknameCheckResultDTO(
                !isNicknameTaken,
                isNicknameTaken ? "닉네임(아이디)이 이미 사용 중입니다." : "닉네임(아이디)을 사용할 수 있습니다."
        );
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userConverter::convert)
                .orElse(null);
    }
}