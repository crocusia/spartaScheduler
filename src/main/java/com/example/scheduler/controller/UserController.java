package com.example.scheduler.controller;

import com.example.scheduler.dto.UserCreateRequestDto;
import com.example.scheduler.dto.UserDeleteRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.dto.UserUpdateRequestDto;
import com.example.scheduler.service.UserService;
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
    public ResponseEntity<UserResponseDto> createTask(@RequestBody UserCreateRequestDto createDto) {
        return new ResponseEntity<>(userService.saveUser(createDto), HttpStatus.CREATED);
    }

    //유저 Id로 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    //유저 이름 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody UserUpdateRequestDto updateDto
    ) {
        return new ResponseEntity<>(userService.updateUser(id, updateDto), HttpStatus.OK);
    }

    //유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @RequestBody UserDeleteRequestDto deleteDto) {
        userService.deleteUser(id, deleteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
