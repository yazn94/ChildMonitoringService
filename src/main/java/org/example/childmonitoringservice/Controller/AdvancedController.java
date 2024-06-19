package org.example.childmonitoringservice.Controller;

import org.example.childmonitoringservice.custom_annotations.ValidJwtToken;
import org.example.childmonitoringservice.model.advancedModels.GameDTO;
import org.example.childmonitoringservice.util.AdvancedControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
