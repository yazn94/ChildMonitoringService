package org.example.childmonitoringservice.database;

import org.example.childmonitoringservice.model.GameSummary;
import org.example.childmonitoringservice.model.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class DAO {
    private final String CHILD_PRO = "child_profile";

    private final JdbcTemplate jdbcTemplate;
    private final String GAME_SUMMARY_TABLE = "drawingGameSummary";
    private final String PARENT_INSTRUCTION_TABLE = "parent_instruction";
    private final String DOCTOR_INSTRUCTION_TABLE = "doctor_instruction";
    private final String GAMING_PROFILE_TABLE = "gaming_profile";

    @Autowired
    public DAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // adding parent instruction to the database
    public void addParentInstruction(String email, String instruction) {
        String sql = "INSERT INTO " + PARENT_INSTRUCTION_TABLE + " (email, instruction) VALUES (?, ?)";
        jdbcTemplate.update(sql, email, instruction);
    }

    // adding doctor instruction to the database
    public void addDoctorInstruction(String email, String instruction) {
        String sql = "INSERT INTO " + DOCTOR_INSTRUCTION_TABLE + " (email, instruction) VALUES (?, ?)";
        jdbcTemplate.update(sql, email, instruction);
    }

    // retrieving all parent instructions (instruction_id, instruction, email)
    public ArrayList<Instruction> getParentInstructions(String email) {
        String sql = "SELECT instruction_id, instruction, email FROM " + PARENT_INSTRUCTION_TABLE + " WHERE email = ?";
        return (ArrayList<Instruction>) jdbcTemplate.query(sql, (rs, rowNum) -> {
            Instruction instruction = new Instruction();
            instruction.setID(rs.getString("instruction_id"));
            instruction.setInstruction(rs.getString("instruction"));
            instruction.setEmail(rs.getString("email"));
            return instruction;
        }, email);
    }

    // retrieving all doctor instructions (instruction_id, instruction, email)
    public ArrayList<Instruction> getDoctorInstructions(String email) {
        String sql = "SELECT instruction_id, instruction, email FROM " + DOCTOR_INSTRUCTION_TABLE + " WHERE email = ?";
        return (ArrayList<Instruction>) jdbcTemplate.query(sql, (rs, rowNum) -> {
            Instruction instruction = new Instruction();
            instruction.setID(rs.getString("instruction_id"));
            instruction.setInstruction(rs.getString("instruction"));
            instruction.setEmail(rs.getString("email"));
            return instruction;
        }, email);
    }

    // increase games played
    public void increaseGamesPlayed(String email) {
        // check if a record exists for the email
        String sql = "SELECT COUNT(*) FROM " + GAMING_PROFILE_TABLE + " WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        if (count == 0) {
            // if no record exists, create a new record
            String insertSql = "INSERT INTO " + GAMING_PROFILE_TABLE + " (email, totalGamesPlayed, generalFeedback) VALUES (?, 0, '')";
            jdbcTemplate.update(insertSql, email);
        }
        sql = "UPDATE " + GAMING_PROFILE_TABLE + " SET totalGamesPlayed = totalGamesPlayed + 1 WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }

    // update general ml feedback in gaming profile
    public void updateGeneralFeedback(String email, String feedback) {
        // check if a record exists for the email
        String sql = "SELECT COUNT(*) FROM " + GAMING_PROFILE_TABLE + " WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        if (count == 0) {
            // if no record exists, create a new record
            String insertSql = "INSERT INTO " + GAMING_PROFILE_TABLE + " (email, totalGamesPlayed, generalFeedback) VALUES (?, 0, '')";
            jdbcTemplate.update(insertSql, email);
        }
        sql = "UPDATE " + GAMING_PROFILE_TABLE + " SET generalFeedback = ? WHERE email = ?";
        jdbcTemplate.update(sql, feedback, email);
    }

    // get general ml feedback in gaming profile
    public String getGeneralFeedback(String email) {
        // check if a record exists for the email
        String sql = "SELECT COUNT(*) FROM " + GAMING_PROFILE_TABLE + " WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        if (count == 0) {
            // if no record exists, create a new record
            String insertSql = "INSERT INTO " + GAMING_PROFILE_TABLE + " (email, totalGamesPlayed, generalFeedback) VALUES (?, 0, '')";
            jdbcTemplate.update(insertSql, email);
        }
        sql = "SELECT generalFeedback FROM " + GAMING_PROFILE_TABLE + " WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, String.class, email);
    }

    // get total played games
    public int getTotalGamesPlayed(String email) {
        // check if a record exists for the email
        String sql = "SELECT COUNT(*) FROM " + GAMING_PROFILE_TABLE + " WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        if (count == 0) {
            // if no record exists, create a new record
            String insertSql = "INSERT INTO " + GAMING_PROFILE_TABLE + " (email, totalGamesPlayed, generalFeedback) VALUES (?, 0, '')";
            jdbcTemplate.update(insertSql, email);
        }
        sql = "SELECT totalGamesPlayed FROM " + GAMING_PROFILE_TABLE + " WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email);
    }

    // add game summary
    public void addGameSummary(String email, String gameSubject, String gameSummary) {
        String sql = "INSERT INTO " + GAME_SUMMARY_TABLE + " (email, game_subject, game_summary) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, email, gameSubject, gameSummary);
    }

    // get game summaries
    public ArrayList<GameSummary> getGameSummaries(String email) {
        String sql = "SELECT game_subject, game_summary FROM " + GAME_SUMMARY_TABLE + " WHERE email = ?";
        return (ArrayList<GameSummary>) jdbcTemplate.query(sql, (rs, rowNum) -> {
            GameSummary gameSummary = new GameSummary();
            gameSummary.setGameSubject(rs.getString("game_subject"));
            gameSummary.setGameSummary(rs.getString("game_summary"));
            return gameSummary;
        }, email);
    }

    // delete parent instruction
    public void deleteParentInstruction(int instructionId) {
        String sql = "DELETE FROM " + PARENT_INSTRUCTION_TABLE + " WHERE instruction_id = ?";
        jdbcTemplate.update(sql, instructionId);
    }

    // delete doctor instruction
    public void deleteDoctorInstruction(int instructionId) {
        String sql = "DELETE FROM " + DOCTOR_INSTRUCTION_TABLE + " WHERE instruction_id = ?";
        jdbcTemplate.update(sql, instructionId);
    }

    // delete game summary
    public void deleteGameSummary(int gameId) {
        String sql = "DELETE FROM " + GAME_SUMMARY_TABLE + " WHERE game_id = ?";
        jdbcTemplate.update(sql, gameId);
    }

    // get child age
    public LocalDate getChildBirthDate(String childEmail) {
        String query = "SELECT dateOfBirth FROM " + CHILD_PRO + " WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{childEmail}, LocalDate.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
