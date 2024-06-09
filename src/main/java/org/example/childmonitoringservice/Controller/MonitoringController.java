package org.example.childmonitoringservice.Controller;

import org.example.childmonitoringservice.custom_annotations.ValidJwtToken;
import org.example.childmonitoringservice.service.ChildProgressService;
import org.example.childmonitoringservice.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {
    // TODO: ops, forgot about doctor feedback! Need to add that.
    private final ChildProgressService childProfileService;

    @Autowired
    public MonitoringController(ChildProgressService childProfileService) {
        this.childProfileService = childProfileService;
    }

    @GetMapping("get/child/progress")
    @ValidJwtToken
    public ResponseEntity<?> getChildProgress(@RequestHeader("Authorization") String token) {
        String childEmail = JwtTokenUtil.getEmailFromToken(token);
        return ResponseEntity.ok().body(childProfileService.getChildProgress(childEmail));
    }

    @GetMapping("get/parent/feedback")
    @ValidJwtToken
    public ResponseEntity<?> getParentFeedback(@RequestHeader("Authorization") String token) {
        String childEmail = JwtTokenUtil.getEmailFromToken(token);
        return ResponseEntity.ok().body(childProfileService.getChildProgress(childEmail).getParentFeedback());
    }

    @PostMapping("add/parent/feedback")
    @ValidJwtToken
    public ResponseEntity<String> addParentFeedback(@RequestHeader("Authorization") String token, @RequestBody String feedback) {
        String childEmail = JwtTokenUtil.getEmailFromToken(token);
        childProfileService.appendParentFeedback(childEmail, feedback);
        return ResponseEntity.ok().body("Feedback added successfully");
    }

    @GetMapping("get/ml/feedback")
    @ValidJwtToken
    public ResponseEntity<?> getMlFeedback(@RequestHeader("Authorization") String token) {
        String childEmail = JwtTokenUtil.getEmailFromToken(token);
        return ResponseEntity.ok().body(childProfileService.getChildProgress(childEmail).getMachineLearningFeedback());
    }

    @PostMapping("add/ml/feedback")
    @ValidJwtToken
    public ResponseEntity<String> addMlFeedback(@RequestHeader("Authorization") String token, @RequestBody String feedback) {
        String childEmail = JwtTokenUtil.getEmailFromToken(token);
        childProfileService.addMlFeedback(childEmail, feedback);
        return ResponseEntity.ok().body("Feedback added successfully");
    }

    @GetMapping("get/games/played")
    @ValidJwtToken
    public ResponseEntity<?> getGamesPlayed(@RequestHeader("Authorization") String token) {
        String childEmail = JwtTokenUtil.getEmailFromToken(token);
        return ResponseEntity.ok().body(childProfileService.getChildProgress(childEmail).getGamesPlayed());
    }

    // TODO: array items are having "\ as prefix and suffix in the get summary responses, we need to trim that.
    @GetMapping("get/games/summaries")
    @ValidJwtToken
    public ResponseEntity<?> getGameSummaries(@RequestHeader("Authorization") String token) {
        String childEmail = JwtTokenUtil.getEmailFromToken(token);
        return ResponseEntity.ok().body(childProfileService.getChildProgress(childEmail).getGameSummaries());
    }

    // TODO: send email to parent with the summary
    @PostMapping("add/game/summary")
    @ValidJwtToken
    public ResponseEntity<String> addGameSummary(@RequestHeader("Authorization") String token, @RequestBody String summary) {
        String childEmail = JwtTokenUtil.getEmailFromToken(token);
        childProfileService.addGameSummary(childEmail, summary);
        return ResponseEntity.ok().body("Summary added successfully");
    }
}
