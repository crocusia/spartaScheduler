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
    public TaskResponseDto createTask(@RequestBody TaskCreateRequestDto createDto) {
        return taskService.saveTask(createDto);
    }
}
