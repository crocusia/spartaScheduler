package com.example.scheduler.repository;

import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.dto.TaskDto;
import com.example.scheduler.entity.Task;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateTaskRepository implements TaskRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTaskRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 날짜 포맷 (YYYY-MM-DD HH:MM)
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    //DB에 Task 저장 및 저장 내용 반환
    @Override
    public TaskDto saveTask(Task task) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tasks")
                .usingGeneratedKeyColumns("task_id")
                .usingColumns( "user_id", "content", "password");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", task.getUserId());  //작성자 ID
        parameters.put("content", task.getContent());  //할 일
        parameters.put("password", task.getPassword()); //비밀번호

        //일정 고유키 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        //날짜 반환
        String sql = "SELECT updated_at FROM tasks WHERE task_id = ?";
        Timestamp updatedAt = jdbcTemplate.queryForObject(sql, Timestamp.class, key);

        //TaskDto를 생성
        return new TaskDto(key.longValue(), task, updatedAt);
    }

    //userId 또는 updateAt에 해당하는 일정 전체 조회
    @Override
    public List<TaskResponseDto> findTasks(Long userId, String updatedAt){
        //일정 전체 조회
        StringBuilder sql = new StringBuilder(
                "SELECT t.task_id, t.user_id, u.name, t.content, t.updated_at " +
                        "FROM tasks t " +
                        "JOIN users u ON t.user_id = u.user_id "
        );

        // 동적 조건 추가 여부
        List<Object> params = new ArrayList<>();

        if (userId != null) { //유저 아이디를 입력 받았다면
            sql.append("WHERE t.user_id = ? "); //유저 아이디 조건 추가
            params.add(userId);
        }

        if (updatedAt != null && !updatedAt.isEmpty()) { //유저 아이디를 입력 받지 않았다면
            if (userId != null) { //앞에 유저 아이디가 조건으로 추가되어있으면
                sql.append("AND "); //AND 추가
            } else {
                sql.append("WHERE "); //아니면 바로 WHERE 추가 후
            }
            Date date = Date.valueOf(updatedAt);
            sql.append("DATE(t.updated_at) = ? "); //수정일 조건 추가
            params.add(date);
        }

        sql.append("ORDER BY t.updated_at DESC"); //수정일 기준 내림차순 정렬

        return jdbcTemplate.query(sql.toString(), taskRowMapperResponseDto(), params.toArray());
    }

    //id에 해당하는 일정 선택 조회
    @Override
    public TaskDto findTaskByIdOrElseThrow(Long id){
        List<TaskDto> result = jdbcTemplate.query("SELECT task_id, user_id, content, updated_at FROM tasks WHERE task_id = ?", taskRowMapperDto(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다. = " + id));
    }

    //id에 따른 일정 수정을 위한 Task 조회
    @Override
    public Task findTaskByIdWithPwd(Long id){
        List<Task> result = jdbcTemplate.query("SELECT * FROM tasks WHERE task_id = ?", taskRowMapperEntity(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다. = " + id));
    }

    //일정 업데이트
    @Override
    public TaskDto updateTask(Task task){
        int updateRow = jdbcTemplate.update("UPDATE tasks SET user_id = ?, content = ? WHERE task_id = ?", task.getUserId(), task.getContent(), task.getTaskId());
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + task.getTaskId());
        }
        return findTaskByIdOrElseThrow(task.getTaskId());
    }

    //일정 삭제
    @Override
    public int deleteTask(Long id) {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        return jdbcTemplate.update("DELETE FROM tasks WHERE task_id = ?", id);
    }

    //매핑
    private RowMapper<TaskResponseDto> taskRowMapperResponseDto(){
        return new RowMapper<TaskResponseDto>() {
            @Override
            public TaskResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskResponseDto(
                        rs.getLong("task_id"),
                        rs.getString("name"),
                        rs.getString("content"),
                        formatter.format(rs.getTimestamp("updated_at"))
                );
            }
        };
    }

    private RowMapper<TaskDto> taskRowMapperDto(){
        return new RowMapper<TaskDto>() {
            @Override
            public TaskDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskDto(
                        rs.getLong("task_id"),
                        rs.getLong("user_id"),
                        rs.getString("content"),
                        formatter.format(rs.getTimestamp("updated_at"))
                );
            }
        };
    }

    private RowMapper<Task> taskRowMapperEntity(){
        return new RowMapper<Task>() {
            @Override
            public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Task(
                        rs.getLong("task_id"),
                        rs.getLong("user_id"),
                        rs.getString("content"),
                        rs.getString("password")
                );
            }
        };
    }

}
