package org.example.childmonitoringservice.model.advancedModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedbackRequestBody {
    private int childAge;
    private ArrayList<String> parentInstructions;
    private ArrayList<String> doctorInstructions;
    private String drawingSubject;
    private String screenshot;
}
