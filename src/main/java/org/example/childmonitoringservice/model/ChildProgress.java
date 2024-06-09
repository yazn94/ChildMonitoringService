package org.example.childmonitoringservice.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Data
public class ChildProgress {

    // Array of all parent submitted feedbacks
    @JsonProperty("parentFeedback")
    private List<String> parentFeedback;

    // Machine learning feedback about the child's progress so far in the process of learning
    @JsonProperty("machineLearningFeedback")
    private String machineLearningFeedback;

    // Array of summaries of each game played
    @JsonProperty("gameSummaries")
    private List<String> gameSummaries;

    // Number of games played by the child
    @JsonProperty("gamesPlayed")
    private int gamesPlayed;
}
