package com.example.scheduler.service;

import com.example.scheduler.dto.*;
import com.example.scheduler.entity.Task;
import com.example.scheduler.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    //생성 - CreateDto로 받은 내용으로 저장
    @Override
    public TaskResponseDto saveTask(TaskCreateRequestDto createDto) {
        // 요청받은 데이터로 Task 객체 생성
        Task task = new Task(createDto.getUserId(), createDto.getContent(), createDto.getPassword());
        // Repository에 저장
        TaskDto taskDto = taskRepository.saveTask(task);
        // 유저 Id 기반 유저 조회
        // 해당 유저 Id가 존재하는지 검증을 해야하지만, 프로그램 기획상 이미 존재하는 유저의 리스트 중에서 선택했다는 가정하에 필요치 않음
        String name = userService.findUserName(createDto.getUserId());
        return new TaskResponseDto(name, taskDto);
    }

    //조회 - 조건을 만족하는 일정 전체 -> 얘만 조인으로 리턴하자
    @Override
    public List<TaskResponseDto> findTasks(Long userId, String updatedAt) {
        //유저 아이디와 수정일은 리스트에서 선택했다는 가정하에 검증하지 않음
        return taskRepository.findTasks(userId, updatedAt);
    }

    //조회 - id를 통한 단일 일정 조회
    @Override
    public TaskResponseDto findTaskById(Long id) {
        TaskDto taskDto = taskRepository.findTaskByIdOrElseThrow(id);
        // 유저 Id 기반 유저 이름 조회
        String name = userService.findUserName(taskDto.getUserId());
        return new TaskResponseDto(name, taskDto);
    }

    //수정 - userId 또는 content 수정
    @Transactional
    @Override
    public TaskResponseDto updateTask(Long id, TaskUpdateRequestDto updateDto) {
        //기존 일정 조회
        Task task = taskRepository.findTaskByIdWithPwd(id);
        //비밀번호 비교
        if (!task.comparePassword(updateDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호 입력 오류");
        }
        //작성자 Id 수정
        if (updateDto.getUserId() != null) {
            //유저 아이디는 리스트에서 선택했다는 가정하에 검증하지 않음
            //유저 이름은 유저 레포지토리에서 별도로 변경, 이건 일정을 수행해야 하는 유저를 변경하는 기능
            task.updateUserId(updateDto.getUserId());
        }
        //할 일 수정
        if (updateDto.getContent() != null && !updateDto.getContent().isEmpty()) {
            task.updateContent(updateDto.getContent());
        }
        //수정된 일정 저장
        TaskDto taskDto = taskRepository.updateTask(task);
        // 유저 Id 기반 유저 이름 조회
        String name = userService.findUserName(taskDto.getUserId());
        return new TaskResponseDto(name, taskDto);
    }

    @Override
    public void deleteTask(Long id, TaskDeleteRequestDto deleteDto) {
        //기존 일정 조회
        Task task = taskRepository.findTaskByIdWithPwd(id);
        //조회한 일정의 비밀번호와 넘겨 받은 비밀번호 비교
        if (!task.comparePassword(deleteDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호 입력 오류");
        }
        // 일정 삭제
        int deletedRow = taskRepository.deleteTask(id);
        // 삭제된 row가 0개 라면
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다. = " + id);
        }
    }
}
