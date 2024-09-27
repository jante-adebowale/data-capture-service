package com.janteadebowale.data_capture_api.repository;

import com.janteadebowale.data_capture_api.enums.Role;
import com.janteadebowale.data_capture_api.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.repository
 **********************************************************/
@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean registerUser(User user) {
        String insertUserQuery = "INSERT INTO users(firstname, lastname, email, password, enabled, user_role,id) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertUserQuery);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setBoolean(5, true);
            ps.setString(6, user.getRole().name());
            ps.setString(7, user.getId());
            return ps;
        });
        return returnValue > 0;
    }

  @Transactional
    public Optional<User> findByUsername(String username) {
        String fetchUserQuery = "SELECT * FROM users u  WHERE u.email=?";
        try {
            //fetch model
            User user = jdbcTemplate.queryForObject(fetchUserQuery, (rs, rowNum) -> {
                        User userData = new User();
                        userData.setId(rs.getString("id"));
                        userData.setFirstname(rs.getString("firstname"));
                        userData.setLastname(rs.getString("lastname"));
                        userData.setUsername(username);
                        userData.setEmail(rs.getString("email"));
                        userData.setPassword(rs.getString("password"));
                        userData.setEnabled(rs.getBoolean("enabled"));
                        userData.setRole(Role.valueOf(rs.getString("user_role")));
                        return userData;
                    },
                    username
            );

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> findByUserId(String id) {
        String fetchUserQuery = "SELECT * FROM users  WHERE id=?";
        try {
            //fetch model
            User user = jdbcTemplate.queryForObject(fetchUserQuery, (rs, rowNum) -> {
                        User userData = new User();
                        userData.setId(rs.getString("id"));
                        userData.setFirstname(rs.getString("firstname"));
                        userData.setLastname(rs.getString("lastname"));
                        userData.setUsername(rs.getString("email"));
                        userData.setEmail(rs.getString("email"));
                        userData.setPassword(rs.getString("password"));
                        userData.setEnabled(rs.getBoolean("enabled"));
                        userData.setRole(Role.valueOf(rs.getString("user_role")));
                        return userData;
                    },
                    id
            );

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public static User mapUserRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setEmail(resultSet.getString("email"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setRole(Role.valueOf(resultSet.getString("user_role")));
        return user;
    }


    public Boolean isEmailExist(String email) {
        String countQuery = "SELECT count(id) FROM users WHERE email=?";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class, email);
        return Objects.requireNonNull(count) > 0;
    }

    public boolean updateUserPassword(User user) {
        int update = jdbcTemplate.update("UPDATE users SET password = ? WHERE id = ?", user.getPassword(), user.getId());
        return update > 0;
    }
}
