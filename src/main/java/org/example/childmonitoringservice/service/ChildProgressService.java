package org.example.childmonitoringservice.service;

import org.example.childmonitoringservice.database.DAO;
import org.example.childmonitoringservice.model.ChildProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChildProgressService {
    DAO dao;

    @Autowired
    public ChildProgressService(DAO dao) {
        this.dao = dao;
    }

    public void appendParentFeedback(String email, String feedback) {
        ChildProgress childProgress = dao.getChildProgress(email);
        if (childProgress.getParentFeedback() == null) {
            childProgress.setParentFeedback(new ArrayList<>());
        }
        childProgress.getParentFeedback().add(feedback);
        dao.updateChildProgress(email, childProgress);
    }

    public void addMlFeedback(String email, String feedback) {
        ChildProgress childProgress = dao.getChildProgress(email);
        childProgress.setMachineLearningFeedback(feedback);
        dao.updateChildProgress(email, childProgress);
    }

    public void addGameSummary(String email, String summary) {
        ChildProgress childProgress = dao.getChildProgress(email);
        if (childProgress.getGameSummaries() == null) {
            childProgress.setGameSummaries(new ArrayList<>());
        }
        childProgress.getGameSummaries().add(summary);
        childProgress.setGamesPlayed(childProgress.getGamesPlayed() + 1);
        dao.updateChildProgress(email, childProgress);
    }

    public ChildProgress getChildProgress(String email) {
        return dao.getChildProgress(email);
    }
}
