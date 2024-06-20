package org.example.childmonitoringservice.util;

import org.example.childmonitoringservice.model.advancedModels.*;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class MLIntegrationHelper {
    private static final RestTemplate restTemplate = new RestTemplate();

    public static JSONObject createRequestBody(GenerateGameRequestBody generateGameRequestBody) {
        JSONObject requestBody = new JSONObject();
        requestBody.put(Constants.CHILD_AGE, generateGameRequestBody.getChildAge());
        requestBody.put(Constants.PARENT_INSTRUCTIONS, generateGameRequestBody.getParentInstructions());
        requestBody.put(Constants.DOCTOR_INSTRUCTIONS, generateGameRequestBody.getDoctorInstructions());
        requestBody.put(Constants.PREV_GAMES_SUBJECTS, generateGameRequestBody.getPrevGamesSubjects());
        return requestBody;
    }

    public static JSONObject createRequestBody(FeedbackRequestBody feedbackRequestBody) {
        JSONObject requestBody = new JSONObject();
        requestBody.put(Constants.CHILD_AGE, feedbackRequestBody.getChildAge());
        requestBody.put(Constants.PARENT_INSTRUCTIONS, feedbackRequestBody.getParentInstructions());
        requestBody.put(Constants.DOCTOR_INSTRUCTIONS, feedbackRequestBody.getDoctorInstructions());
        requestBody.put(Constants.DRAWING_SUBJECT, feedbackRequestBody.getDrawingSubject());
        requestBody.put(Constants.DRAWING, feedbackRequestBody.getScreenshot());
        return requestBody;
    }

    public static HttpEntity<String> createRequestEntity(JSONObject requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(requestBody.toString(), headers);
    }

    public static ResponseEntity<String> sendPostRequest(String apiUrl, HttpEntity<String> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error in the response: " + responseEntity.getStatusCode());
        }
        return responseEntity;
    }

    public static GameDTO getGameDTO(ResponseEntity<String> responseEntity) {
        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        GameDTO gameDTO = new GameDTO();
        gameDTO.setSubject(responseJson.getString(Constants.SUBJECT));
        gameDTO.setImage(responseJson.getString(Constants.BASE64_BACKGROUND_IMAGE));
        return gameDTO;
    }

    public static FeedbackDTO getFeedback(ResponseEntity<String> responseEntity) {
        FeedbackDTO feedback = new FeedbackDTO();
        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        feedback.setFeedback(responseJson.getString(Constants.ML_FEEDBACK));
        return feedback;
    }
}
