package org.example.childmonitoringservice.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChildProfile {
    private String email;
    private String firstName;
    private String lastName;
    private String parentEmail;
    private String doctorEmail;
    private LocalDate dateOfBirth;
    private ChildProgress progress;
    private int currentPoints;
    private boolean pointsSystemAvailability;
    private LocalDate registerDate;
}
