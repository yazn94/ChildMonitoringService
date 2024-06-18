package org.example.childmonitoringservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameSummaryDTO {
    @Email
    @NotNull
    @NotEmpty
    private String email;
    @NotEmpty
    @NotNull
    private String gameSubject;
    @NotEmpty
    @NotNull
    private String gameSummary;
}
