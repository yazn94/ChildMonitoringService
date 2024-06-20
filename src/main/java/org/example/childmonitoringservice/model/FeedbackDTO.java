package org.example.childmonitoringservice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackDTO {
    @NotNull
    @NotEmpty
    private String feedback;
}
