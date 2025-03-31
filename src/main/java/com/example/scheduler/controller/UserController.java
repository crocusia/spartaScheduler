package com.example.scheduler.controller;

import com.example.scheduler.dto.UserCreateRequestDto;
import com.example.scheduler.dto.UserDeleteRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.dto.UserUpdateRequestDto;
import com.example.scheduler.repository.UserRepository;
import com.example.scheduler.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody
@RequestMapping("/users") // Prefix
public class UserController {
    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    //유저 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto createDto) {
        UserResponseDto responseDto = userService.saveUser(createDto);
        return ResponseEntity.ok(responseDto);
    }

    //유저 Id로 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findUserById(id);
        return ResponseEntity.ok(responseDto);
    }

    //유저 이름 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequestDto updateDto
    ) {
        UserResponseDto responseDto = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(responseDto);
    }

    //유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @RequestBody UserDeleteRequestDto deleteDto) {
        userService.deleteUser(id, deleteDto);
        return ResponseEntity.ok().build();
    }
}
