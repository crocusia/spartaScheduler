package com.example.scheduler.repository;

import com.example.scheduler.dto.TaskDto;
import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.entity.Task;
import com.example.scheduler.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //유저 생성
    @Override
    public UserResponseDto saveUser(User user){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id")
                .usingColumns( "name", "email", "password");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());  //작성자 ID
        parameters.put("email", user.getEmail());  //할 일
        parameters.put("password", user.getPassword()); //비밀번호

        //유저 고유키 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        //날짜 반환
        String sql = "SELECT updated_at FROM users WHERE user_id = ?";
        Timestamp updatedAt = jdbcTemplate.queryForObject(sql, Timestamp.class, key);

        //UserResponseDto를 생성
        return new UserResponseDto(key.longValue(), user.getName(), user.getEmail(), updatedAt);
    }

    //유저 조회
    @Override
    public UserResponseDto findUserByIdOrElseThrow(Long id){
        List<UserResponseDto> result = jdbcTemplate.query("SELECT user_id, name, email, updated_at FROM users WHERE user_id = ?", userRowMapperResponseDto(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다. = " + id));
    }

    //유저 이름만 조회! userId에 해당하는 이름 조회
    @Override
    public String findUserName(Long id){
        String sql = "SELECT name FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    @Override
    public User findUserWithPwd(Long id){
        List<User> result = jdbcTemplate.query("SELECT user_id, name, email, password FROM users WHERE user_id = ?", userRowMapperEntity(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다. = " + id));
    }

    @Override
    public boolean checkUserExist(Long id){
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    //유저 이름 수정
    @Override
    public UserResponseDto updateUser(Long id, String name){
        int updateRow = jdbcTemplate.update("UPDATE users SET name = ? WHERE user_id = ?", name, id);
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디의 유저가 존재하지 않습니다. = " + id);
        }
        return findUserByIdOrElseThrow(id);
    }
    //유저 삭제
    @Override
    public int deleteUser(Long id){
        String sql = "DELETE FROM users WHERE user_id = ?";
        return jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", id);
    }

    //매핑
    private RowMapper<UserResponseDto> userRowMapperResponseDto(){
        return new RowMapper<UserResponseDto>() {
            @Override
            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserResponseDto(
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("updated_at")
                );
            }
        };
    }
    private RowMapper<User> userRowMapperEntity(){
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        };
    }
}
