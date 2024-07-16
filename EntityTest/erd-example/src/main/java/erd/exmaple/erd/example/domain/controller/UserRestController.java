package erd.exmaple.erd.example.domain.controller;

import erd.exmaple.erd.example.domain.dto.UserRequestDTO;
import erd.exmaple.erd.example.domain.dto.UserResponseDTO;
import erd.exmaple.erd.example.domain.dto.UserPhoneNumberCheckResultDTO;
import erd.exmaple.erd.example.domain.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

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
}