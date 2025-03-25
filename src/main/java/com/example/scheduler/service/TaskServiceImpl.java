package com.example.scheduler.service;

import com.example.scheduler.dto.TaskCreateRequestDto;
import com.example.scheduler.dto.TaskDto;
import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.dto.TaskUpdateRequestDto;
import com.example.scheduler.entity.Task;
import com.example.scheduler.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //생성 - CreateDto로 받은 내용으로 저장
    @Override
    public TaskResponseDto saveTask(TaskCreateRequestDto createDto) {
        // 요청받은 데이터로 Task 객체 생성
        Task task = new Task(createDto.getUserId(), createDto.getContent(), createDto.getPassword());
        // Repository에 저장
        TaskDto taskDto = taskRepository.saveTask(task);
        // 유저 Id 기반 유저 조회
        String name = "조회 중";
        return new TaskResponseDto(name, taskDto);
    }

    //조회 - 조건을 만족하는 일정 전체 -> 얘만 조인으로 리턴하자
    @Override
    public List<TaskResponseDto> findTasks(Long userId, String updatedAt){
        return taskRepository.findTasks(userId, updatedAt);
    }

    //조회 - id를 통한 단일 일정 조회
    @Override
    public TaskResponseDto findTaskById(Long id){
        TaskDto taskDto = taskRepository.findTaskByIdOrElseThrow(id);
        // 유저 Id 기반 유저 조회
        String name = "조회 중";
        return new TaskResponseDto(name, taskDto);
    }

    //수정 - userId 또는 content 수정
    @Transactional
    @Override
    public TaskResponseDto updateTask(Long id, TaskUpdateRequestDto updateDto){
        //기존 일정 조회
        Task task = taskRepository.findTaskByIdWithPwd(id);
        //비밀번호 비교
        if (!task.getPassword().equals(updateDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호 입력 오류");
        }
        //작성자 Id 수정
        if (updateDto.getUserId() != null){
            //유저레포지토리에서 조회 후 반환된 결과에 따라 반영 - 실제 존재하는가?
            //유저아이디로 해당 일정을 작성한 유저만 변경
            //유저 이름은 유저 레포지토리에서 변경
            task.updateUserId(updateDto.getUserId());
        }
        //할 일 수정
        if(updateDto.getContent() != null && !updateDto.getContent().isEmpty()){
            task.updateContent(updateDto.getContent());
        }
        //수정된 일정 저장
        taskRepository.updateTask(task);
        //수정된 일정의 내용을 TaskResponseDto 형태로 찾아서 반환
        return findTaskById(task.getTaskId());
    }

}
