package org.example.childmonitoringservice.Controller;

import org.example.childmonitoringservice.custom_annotations.ValidJwtToken;
import org.example.childmonitoringservice.model.advancedModels.FeedbackDTO;
import org.example.childmonitoringservice.model.advancedModels.FeedbackRequestInput;
import org.example.childmonitoringservice.model.advancedModels.GameDTO;
import org.example.childmonitoringservice.util.AdvancedControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitoring/advanced")
public class AdvancedController {
    private AdvancedControllerHelper advancedControllerHelper;

    @Autowired
    public AdvancedController(AdvancedControllerHelper advancedControllerHelper) {
        this.advancedControllerHelper = advancedControllerHelper;
    }

    @GetMapping("/generate/game")
    @ValidJwtToken
    public GameDTO generateGame(@RequestHeader("Authorization") String token) {
        return advancedControllerHelper.generateGame(token);
    }

    @GetMapping("/get/feedback")
    @ValidJwtToken
    public FeedbackDTO getFeedback(@RequestHeader("Authorization") String token, @RequestBody FeedbackRequestInput feedbackRequest) {
        return advancedControllerHelper.getFeedback(token, feedbackRequest);
    }
}
