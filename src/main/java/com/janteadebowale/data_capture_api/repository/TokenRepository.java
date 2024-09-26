package com.janteadebowale.data_capture_api.repository;

import com.janteadebowale.data_capture_api.model.Token;
import com.janteadebowale.data_capture_api.util.DataCaptureUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.repository
 **********************************************************/
@Repository
public class TokenRepository {
    private final JdbcTemplate jdbcTemplate;

    public TokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean saveToken(Token token) {

        String insertQuery = "INSERT INTO access_token(token,refresh_token,user_id) VALUES (?,?,?)";
        int update = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, token.token());
            preparedStatement.setString(2, token.refreshToken());
            preparedStatement.setString(3, token.userId());
            return preparedStatement;
        });
        return update > 0;
    }

    public boolean revokeToken(String userId) {
        String updateQuery = "UPDATE access_token SET expired = ?,revoked = ?, expiry_date = ? WHERE user_id = ?";
        int update = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setBoolean(2, true);
            preparedStatement.setTimestamp(3, DataCaptureUtil.getCurrentSQLDateTimestamp());
            preparedStatement.setString(4, userId);
            return preparedStatement;
        });
        return update > 0;
    }

    public Optional<Token> getTokenByToken(String jwt) {
        try {
            String selectQuery = "SELECT id,refresh_token,user_id,expired,revoked,logout FROM access_token WHERE token = ?";
            var token = jdbcTemplate.queryForObject(selectQuery, (rs, rowNum) ->
                            new Token(rs.getInt("id"), rs.getString("user_id"), jwt, rs.getString("refresh_token"), rs.getBoolean("expired"), rs.getBoolean("revoked"), rs.getBoolean("logout"))
                    , jwt);
            return Optional.ofNullable(token);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    public Optional<Token> getTokenByUserId(String userId) {
        try {
            String selectQuery = "SELECT id,refresh_token,token,user_id,expired,revoked,logout FROM access_token WHERE user_id = ? AND expired = ? AND revoked = ?";
            var token = jdbcTemplate.queryForObject(selectQuery, (rs, rowNum) ->
                            new Token(rs.getInt("id"), rs.getString("user_id"), rs.getString("token"), rs.getString("refresh_token"), rs.getBoolean("expired"), rs.getBoolean("revoked"), rs.getBoolean("logout"))
                    , userId,false,false);
            return Optional.ofNullable(token);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    public Boolean isRefreshTokenValid(String refreshToken,String userId) {
        String countQuery = "SELECT count(id) FROM access_token WHERE refresh_token = ? AND user_id = ?";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class, refreshToken,userId);
        return Objects.requireNonNull(count) > 0;
    }

    public boolean logOut(int tokenId) {
        String updateQuery = "UPDATE access_token SET expired = ?,revoked = ?, expiry_date = ?,logout = ?,logout_date = ? WHERE id = ?";
        int update = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setBoolean(2, true);
            preparedStatement.setTimestamp(3, DataCaptureUtil.getCurrentSQLDateTimestamp());
            preparedStatement.setBoolean(4, true);
            preparedStatement.setTimestamp(5, DataCaptureUtil.getCurrentSQLDateTimestamp());
            preparedStatement.setInt(6, tokenId);
            return preparedStatement;
        });

        return update > 0;


    }

}
