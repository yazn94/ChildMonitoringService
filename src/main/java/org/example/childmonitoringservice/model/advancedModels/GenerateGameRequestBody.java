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
public class GenerateGameRequestBody {
    private int childAge;
    private ArrayList<String> parentInstructions;
    private ArrayList<String> doctorInstructions;
    private ArrayList<String> prevGamesSubjects;
}
