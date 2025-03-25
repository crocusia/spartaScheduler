package com.example.scheduler.controller;

import com.example.scheduler.dto.TaskCreateRequestDto;
import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequestMapping("/tasks") // Prefix
public class TaskController {
    private final TaskService taskService;

    private TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //일정 생성
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskCreateRequestDto createDto) {
        return new ResponseEntity<>(taskService.saveTask(createDto), HttpStatus.CREATED);
    }
    //특정 조건을 만족하는 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findTasks(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String updatedAt) {
        return new ResponseEntity<>(taskService.findTasks(userId, updatedAt), HttpStatus.OK) ;
    }
    //일정 Id에 따른 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.findTaskById(id), HttpStatus.OK);
    }

    //일정 삭제

}
