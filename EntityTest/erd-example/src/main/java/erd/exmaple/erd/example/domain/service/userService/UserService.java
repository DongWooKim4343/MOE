package erd.exmaple.erd.example.domain.service.userService;

import erd.exmaple.erd.example.domain.dto.UserRequestDTO;
import erd.exmaple.erd.example.domain.dto.UserResponseDTO;
import erd.exmaple.erd.example.domain.dto.NicknameCheckResultDTO;

public interface UserService {
    UserResponseDTO.JoinResultDTO joinUser(UserRequestDTO.JoinDto joinDto);
    NicknameCheckResultDTO checkNickname(String nickname);
    UserResponseDTO getUserById(Long userId);
}