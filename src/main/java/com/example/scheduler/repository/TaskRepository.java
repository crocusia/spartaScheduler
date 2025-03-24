package com.example.scheduler.repository;

import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    TaskResponseDto saveTask(Task task);
}
