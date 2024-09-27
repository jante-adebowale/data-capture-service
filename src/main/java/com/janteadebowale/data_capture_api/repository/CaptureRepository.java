package com.janteadebowale.data_capture_api.repository;

import com.janteadebowale.data_capture_api.model.Analytic;
import com.janteadebowale.data_capture_api.model.Capture;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.repository
 **********************************************************/
@Repository
public class CaptureRepository {
    private final JdbcTemplate jdbcTemplate;

    public CaptureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addCapture(Capture capture) {
        String insertUserQuery = "INSERT INTO captures(firstname, lastname,age_range,longitude,latitude,user_id,approved,id) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertUserQuery);
            ps.setString(1, capture.getFirstname());
            ps.setString(2, capture.getLastname());
            ps.setInt(3, capture.getAge());
            ps.setString(4, capture.getLongitude());
            ps.setString(5, capture.getLatitude());
            ps.setString(6, capture.getUserId());
            ps.setBoolean(7, capture.isApproved());
            ps.setString(8, capture.getId());
            return ps;
        });
        return returnValue > 0;
    }

    //SELECT approved, COUNT(approved) FROM captures WHERE user_id = '1' GROUP BY approved

    public List<Analytic> getCaptureAnalytics(String userId) {
        List<Analytic> analyticList = jdbcTemplate.query("SELECT approved, COUNT(approved) FROM captures WHERE user_id = ? GROUP BY approved", CaptureRepository::mapAnalyticRow,userId);
        return analyticList;
    }
    public List<Capture> getApprovedCaptures() {
        List<Capture> captureList = jdbcTemplate.query("SELECT * FROM captures WHERE approved = true", CaptureRepository::mapCaptureRow);
        return captureList;
    }

    public List<Capture> getDeclinedCaptures() {
        List<Capture> captureList = jdbcTemplate.query("SELECT * FROM captures WHERE approved = false", CaptureRepository::mapCaptureRow);
        return captureList;
    }

    public static Capture mapCaptureRow(ResultSet resultSet, int rowNum) throws SQLException {
        Capture capture = new Capture();
        capture.setId(resultSet.getString("id"));
        capture.setFirstname(resultSet.getString("firstname"));
        capture.setLastname(resultSet.getString("lastname"));
        capture.setAge(resultSet.getInt("age"));
        capture.setApproved(resultSet.getBoolean("approved"));
        capture.setLongitude(resultSet.getString("longitude"));
        capture.setLatitude(resultSet.getString("latitude"));
        capture.setUserId(resultSet.getString("user_id"));
        return capture;
    }

    public static Analytic mapAnalyticRow(ResultSet resultSet, int rowNum) throws SQLException {
        Analytic analytic = new Analytic();
        analytic.setStatus(resultSet.getBoolean("approved"));
        analytic.setCount(resultSet.getInt("count"));
        return analytic;
    }

    public Boolean isCaptureExist(String id) {
        String countQuery = "SELECT count(id) FROM captures WHERE id=?";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class, id);
        return Objects.requireNonNull(count) > 0;
    }
}
