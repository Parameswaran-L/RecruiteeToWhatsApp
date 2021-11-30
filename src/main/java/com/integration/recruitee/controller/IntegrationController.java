package com.integration.recruitee.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.recruitee.model.Payload;
import com.integration.recruitee.model.RecrutieeResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/integration")
public class IntegrationController {
    final String ACCOUNT_SID = "AC441d5adf730e937ea1a895fa490449d9";
    final String AUTH_TOKEN = "d80a19dd7e0435fa59184490d399e880";
    final String TWILIO_SANDBOX_NUMBER = "whatsapp:+14155238886";

    @GetMapping("/api")
    public CompletableFuture<String> integrationByRecrutiee(@RequestBody RecrutieeResponse recrutieeResponse)
            throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {

        //Important
        Payload payload = recrutieeResponse.payload;
        String ToPipeLine = payload.details.toStage.name;
        String candidateName = payload.getCandidate().name;
        String contactNo = payload.getCandidate().phones.get(0).toString();
        String appliedPosition = payload.getOffer().title;
        String companyName = payload.getCompany().name;
        String message = "";

        switch (ToPipeLine) {
            case "Applied":
                message = "Hi " + candidateName + "\n" +
                        "Thank you for your interest to work at " + companyName + " for the position " + appliedPosition + ". We have received your resume and our team will get back to you shortly.";
                callTwilioWhatsappApi(contactNo, message);
                break;
            case "Ideas2IT - Intro":
                message = "Hi " + candidateName + "\n"
                        + " Ideas2IT is a great place to grow your career. "
                        + "You get the opportunity to chart your own career path,"
                        + " where you could choose to be a technical expert, entrepreneur, or even an entrepreneu."
                        + " Please find JD" + " https://ideas2ittechnologies.recruitee.com/o/";
                callTwilioWhatsappApi(contactNo, message);
                break;
            case "Ideas2IT | L1 Interview":
                message = "Hi " + candidateName  + "\n"
                        + "\n" + "Congratulations!\n"
                        + "\n" + "It was great connecting with you. "
                        + "Your Profile has been shortlisted for L1 interview process."
                        + " Google meet invitation will be sent to your register mail address.";
                callTwilioWhatsappApi(contactNo, message);
                break;
            case "Final Interview":
                message = "Hi" + candidateName  + "\n" +
                        "\n" + "Congratulations!\n" +
                        "\n" + "!!! Your have cleared L1 interview.Google meet invitation will be sent to " +
                        "your register mail address.";
                callTwilioWhatsappApi(contactNo, message);
                break;
            case "Rejection":
                message = "Hi " + candidateName + "\n" +"Thank you for taking the time to meet with our team about the "
                        + appliedPosition +" at Ideas2IT. It was a pleasure to learn more about your skills"
                        + " and accomplishments.\n"  + "\n"
                        + "Unfortunately, our team did not select you for further consideration. "
                        + "Often there are some special needs for a particular role that influences this decision.";
                callTwilioWhatsappApi(contactNo, message);
                break;

            case "HR Discussion":
                message = "Hi " + candidateName  + "\n"
                        + "\n" +  "Congratulations!\n"
                        + "\n" +"Your have cleared Final interview.Google meet invitation will be sent to "
                        + "your register mail address for HR Discussion.";
                callTwilioWhatsappApi(contactNo, message);
                break;
            case "Document Collection":
                message = "Hi " + candidateName  + "\n"
                        + "\n" + "Congratulations!\n" + "\n"
                        + "We have been impressed with your Interview background and Ideas2IT "
                        + "would like to formally offer you.Please share us required document "
                        + "which is mention in mail sent to your registered mail address";
                callTwilioWhatsappApi(contactNo, message);
                break;
            case "Offer":
                message = "Hi " + candidateName  + "\n" +
                        "\n" + "Congratulations!\n"
                        + "\n" +"Congratulations on your offer from Ideas2IT! We are excited to bring you on board. "
                        + "Your offer letter is shared to your registered mail address" ;
                callTwilioWhatsappApi(contactNo, message);
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
                message = "Hi " + candidateName  + "\n"
                        + "\n" +"We are sorry to see you go! You are welcome back anytime. "
                        + "We would have loved to work with you but understand your decision and "
                        + "wish you the very best!";
                callTwilioWhatsappApi(contactNo, message);
                break;
        }

        return null;
    }

    private void callTwilioWhatsappApi(String contactNo, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+91"+contactNo),
                new com.twilio.type.PhoneNumber(TWILIO_SANDBOX_NUMBER),
                messageBody)
                .create();

        System.out.println(message.getSid());
    }
}
