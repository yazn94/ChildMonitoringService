package org.example.childmonitoringservice.model;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailDTO {
    @Email
    private String email;
}
