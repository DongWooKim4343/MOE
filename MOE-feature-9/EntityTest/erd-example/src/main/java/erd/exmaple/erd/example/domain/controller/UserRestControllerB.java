//package erd.exmaple.erd.example.domain.controller;
//
//
//
//import erd.exmaple.erd.example.domain.UserEntity;
//import erd.exmaple.erd.example.domain.enums.Ad;
//import erd.exmaple.erd.example.domain.enums.LoginStatus;
//import erd.exmaple.erd.example.domain.enums.Marketing;
//import erd.exmaple.erd.example.domain.repository.UserRepository;
//import erd.exmaple.erd.example.domain.dto.UserRequestDTO;
//import erd.exmaple.erd.example.domain.dto.UserResponseDTO;
//import erd.exmaple.erd.example.domain.dto.NicknameCheckResultDTO;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//        import java.time.LocalDateTime;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/users")
//public class UserRestController {
//    private final UserRepository userRepository;
//
//    @PostMapping("/join")
//    public ResponseEntity<UserResponseDTO.JoinResultDTO> joinUser(@RequestBody @Valid UserRequestDTO.JoinDto joinDto) {
//
//        // 비밀번호 확인
//        if (!joinDto.getPassword().equals(joinDto.getConfirmPassword())) {
//            //비밀번호가 일치하지 않는 경우 응답
//            return ResponseEntity.badRequest().body(UserResponseDTO.JoinResultDTO.builder()
//                    .isSuccess(false)
//                    .message("비밀번호가 일치하지 않습니다.")
//                    .build());
//        }
//        // 닉네임(아이디) 중복 확인
//        if (userRepository.findByNickname(joinDto.getNickname()).isPresent()) {
//            //닉네임(아이디)이 이미 사용중일때 응답
//            return ResponseEntity.badRequest().body(UserResponseDTO.JoinResultDTO.builder()
//                    .isSuccess(false)
//                    .message("닉네임이 이미 사용 중입니다.")
//                    .build());
//        }
//
//        // UserEntity 생성
//        UserEntity newUser = UserEntity.builder()
//                .password(joinDto.getPassword())
//                .phoneNumber(joinDto.getPhoneNumber())
//                .nickname(joinDto.getNickname())
//                .status(LoginStatus.ACTIVE) // 기본 상태 설정
//                .ad(Ad.ACTIVE) // 기본 광고 상태 설정
//                .marketing(Marketing.ACTIVE) // 기본 마케팅 상태 설정
//                .build();
//
//        // 사용자 저장
//        UserEntity savedUser = userRepository.save(newUser);
//
//        // 응답 DTO 생성
//        UserResponseDTO.JoinResultDTO response = UserResponseDTO.JoinResultDTO.builder()
//                .user_id(savedUser.getId())
//                .created_at(LocalDateTime.now())
//                .isSuccess(true)
//                .message("회원가입이 성공적으로 완료되었습니다.")
//                .build();
//
//        return ResponseEntity.ok(response); //성공했을때 응답
//    }
//
//    //닉네임(아이디) 중복 체크용
//    @GetMapping("/check-nickname")
//    public ResponseEntity<NicknameCheckResultDTO> checkNickname(@RequestParam String nickname) {
//        boolean isNicknameTaken = userRepository.findByNickname(nickname).isPresent();
//
//        return ResponseEntity.ok(new NicknameCheckResultDTO(
//                !isNicknameTaken, // 사용 가능 여부
//                isNicknameTaken ? "닉네임(아이디)이 이미 사용 중입니다." : "닉네임(아이디)을 사용할 수 있습니다."
//        ));
//    }
//
//    // 사용자 조회용
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserEntity> getUserById(@PathVariable Long userId) {
//        return userRepository.findById(userId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//}