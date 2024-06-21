package org.example.childmonitoringservice.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.childmonitoringservice.custom_annotations.ValidJwtToken;
import org.example.childmonitoringservice.model.*;
import org.example.childmonitoringservice.service.ChildProgressHelper;
import org.example.childmonitoringservice.util.EmailHelperUtil;
import org.example.childmonitoringservice.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {
    private final ChildProgressHelper childProgressHelper;
    private final EmailHelperUtil emailHelperUtil;

    @Autowired
    public MonitoringController(ChildProgressHelper childProgressHelper,
                                EmailHelperUtil emailHelperUtil) {
        this.childProgressHelper = childProgressHelper;
        this.emailHelperUtil = emailHelperUtil;
    }

    // add parent instruction (by parent)
    @PostMapping("add/parent/instruction")
    @ValidJwtToken
    public void addParentInstruction(@RequestHeader("Authorization") String token,
                                     @RequestBody @NotNull @Valid InstructionDTO instruction,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }
        childProgressHelper.addParentInstruction(instruction.getEmail(), instruction.getInstruction());
    }

    // add doctor instruction (by doctor)
    @PostMapping("add/doctor/instruction")
    @ValidJwtToken
    public void addDoctorInstruction(@RequestHeader("Authorization") String token,
                                     @RequestBody @NotNull @Valid InstructionDTO instruction,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }
        childProgressHelper.addDoctorInstruction(instruction.getEmail(), instruction.getInstruction());


        String doctorEmail = JwtTokenUtil.getEmailFromToken(token);
        String childEmail = instruction.getEmail();
        String instructionText = instruction.getInstruction();
        String doctorUsername = JwtTokenUtil.getUsernameFromToken(token);
        String parentEmail = childProgressHelper.getParentEmail(childEmail);
        String childFirstName = childProgressHelper.getChildFirstName(childEmail);

        emailHelperUtil.SendDoctorInstructionToParentEmail(parentEmail,
                doctorEmail,
                doctorUsername,
                instructionText,
                childFirstName);
    }

    // get parent instructions (by parent or doctor)
    @PostMapping("get/parent/instructions")
    @ValidJwtToken
    public ArrayList<Instruction> getParentInstructions(@RequestHeader("Authorization") String token,
                                                        @RequestBody @NotNull @Valid EmailDTO childEmail) {
        return childProgressHelper.getParentInstructions(childEmail.getEmail());
    }

    // get doctor instructions (by parent or doctor)
    @PostMapping("get/doctor/instructions")
    @ValidJwtToken
    public ArrayList<Instruction> getDoctorInstructions(@RequestHeader("Authorization") String token,
                                                        @RequestBody @NotNull @Valid EmailDTO childEmail) {
        return childProgressHelper.getDoctorInstructions(childEmail.getEmail());
    }

    // increase games played (by child)
    @PostMapping("increase/games/played")
    @ValidJwtToken
    public void increaseGamesPlayed(@RequestHeader("Authorization") String token) {
        String email = JwtTokenUtil.getEmailFromToken(token);
        childProgressHelper.increaseGamesPlayed(email);
    }

    // update general ml feedback in gaming profile (by child)
    @PostMapping("update/general/feedback")
    @ValidJwtToken
    public void updateGeneralFeedback(@RequestHeader("Authorization") String token,
                                      @RequestBody @Valid @NotNull FeedbackDTO feedback,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }
        String email = JwtTokenUtil.getEmailFromToken(token);
        childProgressHelper.updateGeneralFeedback(email, feedback.getFeedback());
    }

    // get general ml feedback in gaming profile (by parent or doctor)
    @PostMapping("get/general/feedback")
    @ValidJwtToken
    public FeedbackDTO getGeneralFeedback(@RequestHeader("Authorization") String token,
                                          @RequestBody @NotNull @Valid EmailDTO childEmail,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }
        return new FeedbackDTO(
                childProgressHelper.getGeneralFeedback(childEmail.getEmail())
        );
    }

    // get total played games (by parent or doctor or child)
    @PostMapping("get/total/games/played")
    @ValidJwtToken
    public TotalPlayedGamesDTO getTotalGamesPlayed(@RequestHeader("Authorization") String token,
                                                   @RequestBody @NotNull @Valid EmailDTO childEmail,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }

        return new TotalPlayedGamesDTO(
                childProgressHelper.getTotalGamesPlayed(childEmail.getEmail())
        );
    }

    // add game summary (by child) - after playing a game
    @PostMapping("add/game/summary")
    @ValidJwtToken
    public void addGameSummary(@RequestHeader("Authorization") String token,
                               @RequestBody @Valid @NotNull GameSummaryDTO gameSummary,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }
        childProgressHelper.addGameSummary(gameSummary.getEmail(),
                gameSummary.getGameSubject(),
                gameSummary.getGameSummary());
    }

    // get game summaries (by parent or doctor or child)
    @PostMapping("get/game/summaries")
    @ValidJwtToken
    public ArrayList<GameSummary> getGameSummaries(@RequestHeader("Authorization") String token,
                                                   @RequestBody @NotNull @Valid EmailDTO childEmail,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }
        return childProgressHelper.getGameSummaries(childEmail.getEmail());
    }

    // delete parent instruction (by parent)
    @PostMapping("delete/parent/instruction")
    @ValidJwtToken
    public void deleteParentInstruction(@RequestHeader("Authorization") String token,
                                        @RequestBody IdDTO id,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }
        childProgressHelper.deleteParentInstruction(id.getId());
    }

    // delete doctor instruction (by doctor)
    @PostMapping("delete/doctor/instruction")
    @ValidJwtToken
    public void deleteDoctorInstruction(@RequestHeader("Authorization") String token,
                                        @RequestBody IdDTO id,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request: " + bindingResult.getAllErrors());
        }
        childProgressHelper.deleteDoctorInstruction(id.getId());
    }
}
