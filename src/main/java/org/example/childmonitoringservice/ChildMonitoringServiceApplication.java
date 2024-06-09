package org.example.childmonitoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChildMonitoringServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChildMonitoringServiceApplication.class, args);

//        String progress = "{\n" +
//                "    \"parentFeedback\": [\n" +
//                "        \"Please focus on math skills\",\n" +
//                "        \"Try the memory game next\"\n" +
//                "    ],\n" +
//                "    \"machineLearningFeedback\": \"Child is doing well in math and memory games. Keep up the good work!\",\n" +
//                "    \"gameSummaries\": [\n" +
//                "        \"Completed math game with score 85\",\n" +
//                "        \"Completed memory game with score 70\"\n" +
//                "    ],\n" +
//                "     \"gamesPlayed\": 2\n" +
//                "}\n";
//
//
//        JsonUtil jsonUtil = new JsonUtil();
//        // put that string into a model:
//        ChildProgress childProgress = jsonUtil.fromJson(progress, ChildProgress.class);
//        System.out.println(childProgress.getGameSummaries());
//        System.out.println(childProgress.getGamesPlayed());
//
//        // convert the model back to a string:
//        String progressString = jsonUtil.toJson(childProgress);
//        System.out.println(progressString);
    }

}
