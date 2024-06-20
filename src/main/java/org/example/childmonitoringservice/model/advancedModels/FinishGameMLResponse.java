package org.example.childmonitoringservice.model.advancedModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FinishGameMLResponse {
    private String summary;
    private String encouragingFeedback;
    private String newChildFeedback;
}
