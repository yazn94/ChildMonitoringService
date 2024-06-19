package org.example.childmonitoringservice.util;

import org.example.childmonitoringservice.database.DAO;
import org.example.childmonitoringservice.model.GameSummary;
import org.example.childmonitoringservice.model.Instruction;
import org.example.childmonitoringservice.model.advancedModels.GameDTO;
import org.example.childmonitoringservice.model.advancedModels.GenerateGameRequestBody;
import org.example.childmonitoringservice.service.MLIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AdvancedControllerHelper {
    private DAO dao;
    private MLIntegrationService mlIntegrationService;

    @Autowired
    public AdvancedControllerHelper(DAO dao, MLIntegrationService mlIntegrationService) {
        this.dao = dao;
        this.mlIntegrationService = mlIntegrationService;
    }

    public GameDTO generateGame(String token) {
        String email = JwtTokenUtil.getEmailFromToken(token);

        // fetch child age
        int age = fetchChildAge(email);

        // fetch parent instructions
        ArrayList<Instruction> parentInstructions = dao.getParentInstructions(email);
        ArrayList<String> instructionStrings = parentInstructions.stream()
                .map(Instruction::getInstruction)
                .collect(Collectors.toCollection(ArrayList::new));


        // fetch doctor instructions
        ArrayList<Instruction> doctorInstructions = dao.getDoctorInstructions(email);
        ArrayList<String> doctorInstructionStrings = doctorInstructions.stream()
                .map(Instruction::getInstruction)
                .collect(Collectors.toCollection(ArrayList::new));

        // fetch previous generated games subjects
        ArrayList<GameSummary> gameSummaries = dao.getGameSummaries(email);
        ArrayList<String> subjects = gameSummaries.stream()
                .map(GameSummary::getGameSubject)
                .collect(Collectors.toCollection(ArrayList::new));

        // generate requeset body
        GenerateGameRequestBody generateGameRequestBody = GenerateGameRequestBody.builder()
                .childAge(age)
                .parentInstructions(instructionStrings)
                .doctorInstructions(doctorInstructionStrings)
                .prevGamesSubjects(subjects)
                .build();

        return mlIntegrationService.generateGame(generateGameRequestBody);
    }


    private int fetchChildAge(String childEmail) {
        LocalDate date = dao.getChildBirthDate(childEmail);
        LocalDate now = LocalDate.now();
        return Period.between(date, now).getYears();
    }
}
