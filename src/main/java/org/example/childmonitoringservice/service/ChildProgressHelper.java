package org.example.childmonitoringservice.service;

import org.example.childmonitoringservice.database.DAO;
import org.example.childmonitoringservice.model.GameSummary;
import org.example.childmonitoringservice.model.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChildProgressHelper {

    private final DAO dao;

    @Autowired
    public ChildProgressHelper(DAO dao) {
        this.dao = dao;
    }

    // add parent instruction
    public void addParentInstruction(String email, String instruction) {
        dao.addParentInstruction(email, instruction);
    }

    // add doctor instruction
    public void addDoctorInstruction(String email, String instruction) {
        dao.addDoctorInstruction(email, instruction);
    }

    // get parent instructions
    public ArrayList<Instruction> getParentInstructions(String email) {
        return dao.getParentInstructions(email);
    }

    // get doctor instructions
    public ArrayList<Instruction> getDoctorInstructions(String email) {
        return dao.getDoctorInstructions(email);
    }

    // increase games played
    public void increaseGamesPlayed(String email) {
        dao.increaseGamesPlayed(email);
    }

    // update general ml feedback in gaming profile
    public void updateGeneralFeedback(String email, String feedback) {
        dao.updateGeneralFeedback(email, feedback);
    }

    // get general ml feedback in gaming profile
    public String getGeneralFeedback(String email) {
        return dao.getGeneralFeedback(email);
    }

    // get total played games
    public int getTotalGamesPlayed(String email) {
        return dao.getTotalGamesPlayed(email);
    }

    // add game summary
    public void addGameSummary(String email, String gameSubject, String gameSummary) {
        dao.addGameSummary(email, gameSubject, gameSummary);
    }

    // get game summaries
    public ArrayList<GameSummary> getGameSummaries(String email) {
        return dao.getGameSummaries(email);
    }

    // delete parent instruction
    public void deleteParentInstruction(int instructionId) {
        dao.deleteParentInstruction(instructionId);
    }

    // delete doctor instruction
    public void deleteDoctorInstruction(int instructionId) {
        dao.deleteDoctorInstruction(instructionId);
    }

    // get parent email
    public String getParentEmail(String childEmail) {
        return dao.getParentEmail(childEmail);
    }

    // get child first name
    public String getChildFirstName(String childEmail) {
        return dao.getChildFirstName(childEmail);
    }
}

