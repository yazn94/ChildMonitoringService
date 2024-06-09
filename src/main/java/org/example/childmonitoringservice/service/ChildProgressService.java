package org.example.childmonitoringservice.service;

import org.example.childmonitoringservice.database.DAO;
import org.example.childmonitoringservice.model.ChildProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChildProgressUtil {
    DAO dao;

    @Autowired
    public ChildProgressUtil(DAO dao) {
        this.dao = dao;
    }

    public ChildProgress appendParentFeedback(ChildProgress childProgress, String feedback) {
        if (childProgress.getParentFeedback() == null) {
            childProgress.setParentFeedback(new ArrayList<>());
        }
        childProgress.getParentFeedback().add(feedback);
        return childProgress;
    }

    public ChildProgress addMlFeedback(ChildProgress childProgress, String feedback) {
        childProgress.setMachineLearningFeedback(feedback);
        return childProgress;
    }

    public ChildProgress incrementGamesPlayed(ChildProgress childProgress) {
        childProgress.setGamesPlayed(childProgress.getGamesPlayed() + 1);
        return childProgress;
    }

    public ChildProgress addGameSummary(ChildProgress childProgress, String summary) {
        if (childProgress.getGameSummaries() == null) {
            childProgress.setGameSummaries(new ArrayList<>());
        }
        childProgress.getGameSummaries().add(summary);
        return childProgress;
    }

    public ChildProgress getChildProgress(String email) {
        return dao.getChildProgress(email);
    }
}
