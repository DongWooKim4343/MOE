package erd.exmaple.erd.example.domain.service.userService;


import erd.exmaple.erd.example.domain.UserEntity;
import erd.exmaple.erd.example.domain.converter.UserConverter;
import erd.exmaple.erd.example.domain.repository.UserRepository;
import erd.exmaple.erd.example.domain.dto.UserRequestDTO;
import erd.exmaple.erd.example.domain.dto.UserResponseDTO;
import erd.exmaple.erd.example.domain.dto.UserPhoneNumberCheckResultDTO;
import erd.exmaple.erd.example.domain.enums.Ad;
import erd.exmaple.erd.example.domain.enums.LoginStatus;
import erd.exmaple.erd.example.domain.enums.Marketing;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    //private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    @Override
    public UserResponseDTO.JoinResultDTO joinUser(UserRequestDTO.JoinDto joinDto) {
        // 비밀번호 확인
        if (!joinDto.getPassword().equals(joinDto.getConfirmPassword())) {
            return UserResponseDTO.JoinResultDTO.builder()
                    .isSuccess(false)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
        }
        // 핸드폰 번호 중복 확인
        Optional<UserEntity> existingUser = userRepository.findByPhoneNumber(joinDto.getPhoneNumber());
        if (existingUser.isPresent()) {
            return UserResponseDTO.JoinResultDTO.builder()
                    .isSuccess(false)
                    .message("핸드폰 번호가 이미 존재합니다.")
                    .build();
        }



/*         아이디(휴대폰번호) 중복 확인
        if (userRepository.findByPhoneNumber(joinDto.getPhoneNumber()).isPresent()) {
            return UserResponseDTO.JoinResultDTO.builder()
                   .isSuccess(false)
                    .message("닉네임이 이미 사용 중입니다.")
                  .build();
        }*/

        // UserEntity 생성
        UserEntity newUser = UserEntity.builder()
                .password(joinDto.getPassword()) // 비밀번호 암호화 해야하는디..
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
                .phoneNumber(savedUser.getPhoneNumber())
                .message("회원가입이 성공적으로 완료되었습니다.")
                .build();
    }

    @Override
    public UserPhoneNumberCheckResultDTO checkPhoneNumber(String phoneNumber) {
        Optional<UserEntity> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if (existingUser.isPresent()) {
            return UserPhoneNumberCheckResultDTO.builder()
                    .isSuccess(false)
                    .message("핸드폰 번호가 이미 존재합니다.")
                    .build();
        }
        return UserPhoneNumberCheckResultDTO.builder()
                .isSuccess(true)
                .message("사용 가능한 핸드폰 번호입니다.")
                .build();
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userConverter::convert)
                .orElse(null);
    }
}