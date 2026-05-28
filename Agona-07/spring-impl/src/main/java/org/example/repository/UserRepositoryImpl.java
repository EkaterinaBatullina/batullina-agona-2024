package org.example.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.example.model.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_GET_BY_ID = "select * from account where uuid = '%s'";
    private final static String SQL_GET_ALL = "select * from account";
    private final static String SQL_INSERT_USER = "insert into account (name, phone) values (?, ?)";
    private final static String SQL_UPDATE_USER = "update account set name = ?, phone = ? where uuid = ?";
    private final static String SQL_DELETE_USER = "delete from account where uuid = ?";
    private RowMapper<UserEntity> rowMapper = (rs, rowNum) -> UserEntity.builder()
            .name(rs.getString("name"))
            .phone(rs.getString("phone"))
            .uuid(rs.getObject("uuid", UUID.class))
            .build();

    @Override
    public Optional<UserEntity> findById(UUID uuid) {
        try (val stream = jdbcTemplate.queryForStream(SQL_GET_BY_ID.formatted(uuid), rowMapper)) {
            return stream.findAny();
        }
    }

    @Override
    public Set<UserEntity> findAll() {
        return new HashSet<>(jdbcTemplate.query(SQL_GET_ALL, rowMapper));
    }

    @Override
    public void save(UserEntity userEntity) {
        jdbcTemplate.update(SQL_INSERT_USER, userEntity.getName(), userEntity.getPhone());
    }

    @Override
    public void update(UserEntity userEntity) {
        jdbcTemplate.update(SQL_UPDATE_USER, userEntity.getName(), userEntity.getPhone(), userEntity.getUuid());
    }

    @Override
    public void deleteById(UUID uuid) {
        jdbcTemplate.update(SQL_DELETE_USER, uuid);
    }
}



