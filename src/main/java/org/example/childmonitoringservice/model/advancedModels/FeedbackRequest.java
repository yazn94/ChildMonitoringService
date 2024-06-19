package org.example.childmonitoringservice.model.advancedModels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FeedbackRequest {
    private String subject;
    private String base64BackgroundImage;
    private ArrayList<String> parentInstructions;
    private ArrayList<String> doctorInstructions;
}
