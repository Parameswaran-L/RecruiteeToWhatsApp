package com.integration.recruitee.controller;

import com.integration.recruitee.model.Payload;
import com.integration.recruitee.model.RecrutieeResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/recrutiee")
@ResponseStatus(HttpStatus.OK)
public class IntegrationController {
    final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
    final String TWILIO_SANDBOX_NUMBER = "whatsapp:+14155238886";

    @PostMapping("/pipelinechange")
    public CompletableFuture<String> pipeLinechange (@RequestBody RecrutieeResponse recrutieeResponse) {

        //Important
        Payload payload = recrutieeResponse.getPayload();
        if(payload!=null)
        {
        String ToPipeLine = payload.getDetails().getToStage().getName();
        String candidateName = payload.getCandidate().getName();
        String contactNo = payload.getCandidate().getPhones().get(0).toString();
        String appliedPosition = payload.getOffer().getTitle();
        String companyName = payload.getCompany().getName();
        String message = "";

        switch (ToPipeLine) {
            case "Applied":
                message = "Hi " + candidateName + "\n" +
                        "Thank you for your interest to work at " + companyName + " for the position " + appliedPosition + ". We have received your resume and our team will get back to you shortly.";
                callTwilioWhatsappAPI(contactNo, message);
                break;
            case "Ideas2IT - Intro":
                message = "Hi " + candidateName + "\n"
                        + " Ideas2IT is a great place to grow your career. "
                        + "You get the opportunity to chart your own career path,"
                        + " where you could choose to be a technical expert, entrepreneur, or even an entrepreneu."
                        + " Please find JD" + " https://ideas2ittechnologies.recruitee.com/o/";
                callTwilioWhatsappAPI(contactNo, message);
                break;
            case "Ideas2IT | L1 Interview":
                message = "Hi " + candidateName + "\n"
                        + "\n" + "Congratulations!\n"
                        + "\n" + "It was great connecting with you. "
                        + "Your Profile has been shortlisted for L1 interview process."
                        + " Google meet invitation will be sent to your register mail address.";
                callTwilioWhatsappAPI(contactNo, message);
                break;
            case "Final Interview":
                message = "Hi" + candidateName + "\n" +
                        "\n" + "Congratulations!\n" +
                        "\n" + "!!! Your have cleared L1 interview.Google meet invitation will be sent to " +
                        "your register mail address.";
                callTwilioWhatsappAPI(contactNo, message);
                break;
            case "Rejection":
                message = "Hi " + candidateName + "\n" + "Thank you for taking the time to meet with our team about the "
                        + appliedPosition + " at Ideas2IT. It was a pleasure to learn more about your skills"
                        + " and accomplishments.\n" + "\n"
                        + "Unfortunately, our team did not select you for further consideration. "
                        + "Often there are some special needs for a particular role that influences this decision.";
                callTwilioWhatsappAPI(contactNo, message);
                break;

            case "HR Discussion":
                message = "Hi " + candidateName + "\n"
                        + "\n" + "Congratulations!\n"
                        + "\n" + "Your have cleared Final interview.Google meet invitation will be sent to "
                        + "your register mail address for HR Discussion.";
                callTwilioWhatsappAPI(contactNo, message);
                break;
            case "Document Collection":
                message = "Hi " + candidateName + "\n"
                        + "\n" + "Congratulations!\n" + "\n"
                        + "We have been impressed with your Interview background and Ideas2IT "
                        + "would like to formally offer you.Please share us required document "
                        + "which is mention in mail sent to your registered mail address";
                callTwilioWhatsappAPI(contactNo, message);
                break;
            case "Offer":
                message = "Hi " + candidateName + "\n" +
                        "\n" + "Congratulations!\n"
                        + "\n" + "Congratulations on your offer from Ideas2IT! We are excited to bring you on board. "
                        + "Your offer letter is shared to your registered mail address";
                callTwilioWhatsappAPI(contactNo, message);
                break;
            case "Ideas2IT: Immediate Joiners":
                break;
            case "Ideas2IT: DOJ in 1 month":
                break;
            case "Ideas2IT: DOJ in 2 month":
                break;
            case "Ideas2IT: DOJ in 3 month":
                break;
            case "Ideas2IT: DOJ in 15 days":
                break;
            case "Declined":
                message = "Hi " + candidateName + "\n"
                        + "\n" + "We are sorry to see you go! You are welcome back anytime. "
                        + "We would have loved to work with you but understand your decision and "
                        + "wish you the very best!";
                callTwilioWhatsappAPI(contactNo, message);
                break;
        }
        }
        return null;
    }

    @PostMapping("/applied")
    public CompletableFuture<String> candidateApplied(@RequestBody RecrutieeResponse recrutieeResponse) {

        //Important
        Payload payload = recrutieeResponse.getPayload();
        if (payload != null) {
            String candidateName = payload.getCandidate().getName();
            String contactNo = payload.getCandidate().getPhones().get(0).toString();
            String appliedPosition = payload.getOffer().getTitle();
            String companyName = payload.getCompany().getName();
            String message = "Hi " + candidateName + "\n" +
                    "Thank you for your interest to work at " + companyName + " for the position " + appliedPosition + ". We have received your resume and our team will get back to you shortly.";
            callTwilioWhatsappAPI(contactNo, message);
        }
    }

    private void callTwilioWhatsappAPI(String contactNo, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+91" + contactNo),
                new com.twilio.type.PhoneNumber(TWILIO_SANDBOX_NUMBER),
                messageBody)
                .create();

        System.out.println("Message Sent Successfully, SID: " + message.getSid());
    }
}
