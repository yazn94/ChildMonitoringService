package org.example.childmonitoringservice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackDTO {
    @NotNull
    @NotEmpty
    private String feedback;
}
