package org.example.childmonitoringservice.service;

import org.example.childmonitoringservice.model.advancedModels.GameDTO;
import org.example.childmonitoringservice.model.advancedModels.GenerateGameRequestBody;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.example.childmonitoringservice.util.MLIntegrationHelper.*;

@Service
public class MLIntegrationService {
    private final String apiPrefix = "https://0924-92-241-36-177.ngrok-free.app";
    private final String generateSubjectEndpoint = "/generate_subject";

    public GameDTO generateGame(GenerateGameRequestBody generateGameRequestBody) {
        String apiUrl = apiPrefix + generateSubjectEndpoint;
        JSONObject requestBody = createRequestBody(generateGameRequestBody);
        HttpEntity<String> requestEntity = createRequestEntity(requestBody);
        ResponseEntity<String> responseEntity = sendPostRequest(apiUrl, requestEntity);
        return getGameDTO(responseEntity);
    }
}
