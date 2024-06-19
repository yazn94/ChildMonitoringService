package org.example.childmonitoringservice.model.advancedModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameDTO {
    private String subject;
    private String image;
}
