package org.example.childmonitoringservice.model.advancedModels;

public class Constants {
    public static final String CHILD_AGE = "child_age";
    public static final String PARENT_INSTRUCTIONS = "parent_instructions";
    public static final String DOCTOR_INSTRUCTIONS = "doctor_instructions";
    public static final String PREV_GAMES_SUBJECTS = "previous_generated_games_subjects";
    public static final String BASE64_BACKGROUND_IMAGE = "image";
    // returned by the ML model at generate_subject API
    public static final String SUBJECT = "subject";
    // sent in the request body to the ML model at get_feedback API
    public static final String DRAWING_SUBJECT = "drawing_subject";
    public static final String DRAWING = "drawing";
    public static final String ML_FEEDBACK = "feedback";
    public static final String SUMMARY = "summary";
    public static final String ENCOURAGING_FEEDBACK = "encouraging_feedback";
    public static final String OLD_CHILD_FEEDBACK = "old_ml_feedback_on_child_level";
    public static final String NEW_CHILD_FEEDBACK = "new_ml_feedback_on_child_level";
}
