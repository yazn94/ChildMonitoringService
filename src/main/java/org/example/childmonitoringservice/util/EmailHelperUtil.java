package org.example.childmonitoringservice.util;


import org.example.childmonitoringservice.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailHelperUtil {
    private EmailSenderService emailSenderService;

    @Autowired
    public EmailHelperUtil(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    public void SendDoctorInstructionToParentEmail(String parentEmail,
                                                   String doctorEmail,
                                                   String doctorUsername,
                                                   String doctorInstruction,
                                                   String childFirstName) {
        String subject = "Doctor's Instruction for " + childFirstName;
        String body = "Dear Parent,\n\n"
                + "your child: "
                + childFirstName
                + " has received the following instruction from DR. "
                + doctorUsername
                + " ( " + doctorEmail + " )\n\n"
                + "\""
                + doctorInstruction
                + "\""
                + "\n\nBest Regards,\nAutism Care App";
        try {
            emailSenderService.sendEmail(parentEmail, subject, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
