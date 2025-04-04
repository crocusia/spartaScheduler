package com.example.scheduler.controller;

import com.example.scheduler.dto.TaskDeleteRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.scheduler.dto.TaskCreateRequestDto;
import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.dto.TaskUpdateRequestDto;
import com.example.scheduler.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController // @Controller + @ResponseBody
@RequestMapping("/tasks") // Prefix
public class TaskController {
    private final TaskService taskService;

    private TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //일정 생성
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskCreateRequestDto createDto) {
        TaskResponseDto taskResponseDto = taskService.saveTask(createDto);
        return ResponseEntity.ok(taskResponseDto);
    }

    //특정 조건을 만족하는 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findTasks(
            @RequestParam(required = false) Optional<Long> userId,
            @RequestParam(required = false, defaultValue = "") String updatedAt) {
        Long userIdValue = userId.orElse(null);
        List<TaskResponseDto> taskResponseDto = taskService.findTasks(userIdValue, updatedAt);
        return ResponseEntity.ok(taskResponseDto);
    }

    //일정 Id에 따른 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findTaskById(@PathVariable Long id) {
        TaskResponseDto taskResponseDto = taskService.findTaskById(id);
        return ResponseEntity.ok(taskResponseDto);
    }

    //일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody TaskUpdateRequestDto updateDto
    ) {
        TaskResponseDto taskResponseDto = taskService.updateTask(id, updateDto);
        return ResponseEntity.ok(taskResponseDto);
    }

    //일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody TaskDeleteRequestDto deleteDto) {
        taskService.deleteTask(id, deleteDto);
        // 성공한 경우
        return ResponseEntity.ok().build();
    }
}
