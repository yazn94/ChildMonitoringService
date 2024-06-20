package org.example.childmonitoringservice.util;

import org.example.childmonitoringservice.database.DAO;
import org.example.childmonitoringservice.model.GameSummary;
import org.example.childmonitoringservice.model.Instruction;
import org.example.childmonitoringservice.model.advancedModels.*;
import org.example.childmonitoringservice.service.ChildProgressHelper;
import org.example.childmonitoringservice.service.MLIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AdvancedControllerHelper {
    private final DAO dao;
    private final MLIntegrationService mlIntegrationService;
    private final ChildProgressHelper childProgressHelper;

    @Autowired
    public AdvancedControllerHelper(DAO dao,
                                    MLIntegrationService mlIntegrationService,
                                    ChildProgressHelper childProgressHelper) {
        this.dao = dao;
        this.mlIntegrationService = mlIntegrationService;
        this.childProgressHelper = childProgressHelper;
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

    public FeedbackDTO getFeedback(String token, FeedbackRequestInput feedbackRequest) {
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


        FeedbackRequestBody feedbackRequestBody = FeedbackRequestBody.builder()
                .childAge(age)
                .parentInstructions(instructionStrings)
                .doctorInstructions(doctorInstructionStrings)
                .drawingSubject(feedbackRequest.getSubject())
                .screenshot(feedbackRequest.getScreenshot())
                .build();

        return mlIntegrationService.getDrawingFeedback(feedbackRequestBody);
    }

    public EncouragingFeedbackDTO finishGame(String token, FinishGameRequestInput finishGameRequest) {
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

        // fetch old child feedback
        String oldChildFeedback = childProgressHelper.getGeneralFeedback(email);

        FinishGameRequestBody finishGameRequestBody = FinishGameRequestBody.builder()
                .childAge(age)
                .parentInstructions(instructionStrings)
                .doctorInstructions(doctorInstructionStrings)
                .drawingSubject(finishGameRequest.getSubject())
                .screenshot(finishGameRequest.getScreenshot())
                .oldChildFeedback(oldChildFeedback)
                .build();
        System.out.println(finishGameRequestBody.toString());

        FinishGameMLResponse finishGameMLResponse = mlIntegrationService.finishGame(finishGameRequestBody);

        System.out.println(finishGameMLResponse.toString());
        // update the database with game summary
        childProgressHelper.addGameSummary(email,
                finishGameRequest.getSubject(),
                finishGameMLResponse.getSummary());

        // update child level feedback
        childProgressHelper.updateGeneralFeedback(email, finishGameMLResponse.getNewChildFeedback());

        // increase games played by child
        childProgressHelper.increaseGamesPlayed(email);

        // return DTO
        return new EncouragingFeedbackDTO(
                finishGameMLResponse.getEncouragingFeedback()
        );
    }

    private int fetchChildAge(String childEmail) {
        LocalDate date = dao.getChildBirthDate(childEmail);
        LocalDate now = LocalDate.now();
        return Period.between(date, now).getYears();
    }
}
