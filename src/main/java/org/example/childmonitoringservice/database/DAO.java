package org.example.childmonitoringservice.database;

import org.example.childmonitoringservice.model.ChildProgress;
import org.example.childmonitoringservice.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DAO {

    private final JdbcTemplate jdbcTemplate;
    private final String CHILD_PRO = "child_profile";

    @Autowired
    public DAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ChildProgress getChildProgress(String childEmail) {
        String query = "SELECT progress FROM " + CHILD_PRO + " WHERE email = ?";
        try {
            String progressJson = jdbcTemplate.queryForObject(query, new Object[]{childEmail}, String.class);
            return JsonUtil.fromJson(progressJson, ChildProgress.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error reading ChildProgress object from JSON", e);
        }
    }

    public void updateChildProgress(String childEmail, ChildProgress progress) {
        String progressJson = JsonUtil.toJson(progress);
        String updateQuery = "UPDATE " + CHILD_PRO + " SET progress = ? WHERE email = ?";
        jdbcTemplate.update(updateQuery, progressJson, childEmail);
    }
}
