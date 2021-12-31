package com.integration.recruitee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.recruitee.model.feedback.Candidate;
import com.integration.recruitee.model.pipeLineChange.Payload;
import com.integration.recruitee.model.pipeLineChange.RecrutieeResponse;
import com.integration.recruitee.model.applyCandidate.AppliedRecrutieeResponse;
import com.integration.recruitee.repository.CandidateRepository;
import com.twilio.rest.api.v2010.account.Message;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recruitee")
@ResponseStatus(HttpStatus.OK)
public class IntegrationController {
    final String NODE_API = "http://localhost:8000/node";

    @Autowired
    private CandidateRepository repo;

    @PostMapping("/pipelinechange")
    public CompletableFuture<String> pipelineChange (@RequestBody RecrutieeResponse recrutieeResponse) {

        //Important
      Payload payload = recrutieeResponse.getPayload();
        if(payload != null)
        {
        String ToPipeLine = payload.getDetails().getToStage().getName();
        String candidateName = payload.getCandidate().getName();
        String contactNo = payload.getCandidate().getPhones().get(0).toString();
        String appliedPosition = payload.getOffer().getTitle();
        String companyName = payload.getCompany().getName();
        String message;
        switch (ToPipeLine) {
            case "Applied":
                message = "Hi " + candidateName + "\n" +
                        "Thank you for your interest to work at " + companyName + " for the position " + appliedPosition + ". We have received your resume and our team will get back to you shortly.";
                callTwilioWhatsappAPI(contactNo, message);
                updateApplicationStatus("Application Under Process", contactNo);
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
                message = "Hi  " + candidateName + "\n"
                        + "\n" + "Congratulations!\n"
                        + "\n" + "It was great connecting with you. "
                        + "Your Profile has been shortlisted for L1 interview process."
                        + " Google meet invitation will be sent to your register mail address.";
                callTwilioWhatsappAPI(contactNo, message);
                updateApplicationStatus("Shortlisted for L1 interview", contactNo);
                break;
            case "Final Interview":
                message = "Hi " + candidateName + "\n" +
                        "\n" + "Congratulations!\n" +
                        "\n" + "Your have cleared L1 interview.Google meet invitation will be sent to " +
                        "your register mail address for next round.";
                callTwilioWhatsappAPI(contactNo, message);
                updateApplicationStatus("Selected for final interview", contactNo);
                break;
            case "Rejection":
                message = "Hi " + candidateName + "\n" + "Thank you for taking the time to meet with our team about the "
                        + appliedPosition + " at Ideas2IT. It was a pleasure to learn more about your skills"
                        + " and accomplishments.\n" + "\n"
                        + "Unfortunately, our team did not select you for further consideration. "
                        + "Often there are some special needs for a particular role that influences this decision.";
                callTwilioWhatsappAPI(contactNo, message);
                updateApplicationStatus("Rejected", contactNo);
                collectFeedback(contactNo);
                break;
            case "HR Discussion":
                message = "Hi " + candidateName + "\n"
                        + "\n" + "Congratulations!\n"
                        + "\n" + "Your have cleared Final interview.Google meet invitation will be sent to "
                        + "your register mail address for HR Discussion.";
                callTwilioWhatsappAPI(contactNo, message);
                updateApplicationStatus("Cleared final interview", contactNo);
                break;
            case "Document Collection":
                message = "Hi " + candidateName + "\n"
                        + "\n" + "Congratulations!\n" + "\n"
                        + "We have been impressed with your Interview background and Ideas2IT "
                        + "would like to formally offer you.Please share us required document "
                        + "which is mention in mail sent to your registered mail address";
                callTwilioWhatsappAPI(contactNo, message);
                updateApplicationStatus("Under Document Verification", contactNo);
                break;
            case "Offer":
                message = "Hi " + candidateName + "\n" +
                        "\n" + "Congratulations!\n"
                        + "\n" + "Congratulations on your offer from Ideas2IT! We are excited to bring you on board. "
                        + "Your offer letter is shared to your registered mail address";
                callTwilioWhatsappAPI(contactNo, message);
                updateApplicationStatus("Offered", contactNo);
                collectFeedback(contactNo);
                break;
            case "Ideas2IT: Immediate Joiners":
            case "Ideas2IT: DOJ in 3 month":
            case "Ideas2IT: DOJ in 1 month":
            case "Ideas2IT: DOJ in 2 month":
            case "Ideas2IT: DOJ in 15 days":
                break;
            case "Declined":
                message = "Hi " + candidateName + "\n"
                        + "\n" + "We are sorry to see you go! You are welcome back anytime. "
                        + "We would have loved to work with you but understand your decision and "
                        + "wish you the very best!";
                callTwilioWhatsappAPI(contactNo, message);
                updateApplicationStatus("Declined", contactNo);
                break;
        }
        }
        return null;
    }

    /**
     * <p>
     *     Update application status by contact number.
     * <p/>
     *
     * @param newStatus
     *          - New status of the application
     * @param contactNo
     *          - Candidate contact no
     */
    private void updateApplicationStatus(String newStatus, String contactNo) {
        try {
            Candidate candidate = repo.findCandidateByContactNo(Long.parseLong(contactNo));
            if (null == candidate) {
                System.out.println("No application available for this contact no " + contactNo);
            } else {
                String oldStatus = candidate.getApplicationStatus();
                candidate.setApplicationStatus(newStatus);
                repo.save(candidate);
                System.out.println("Application status updated successfully from " + oldStatus + " to " + newStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     *     Get application status by contact no.
     * <p/>
     *
     * @param contactNo
     *            - Candidate contact no
     */
    @GetMapping("/get-application-status/{contactNo}")
    public String getApplicationStatus(@PathVariable String contactNo) {
        String status;
        Candidate candidate = repo.findCandidateByContactNo(Long.parseLong(contactNo));
        if (null != candidate) {
            status = candidate.getApplicationStatus();
        } else {
            status = "Not yet Applied";
        }
        System.out.println(status);
        callTwilioWhatsappAPI(contactNo, status);
        return status;
    }

    private void saveCandidate(com.integration.recruitee.model.applyCandidate.Payload payload) {
        String candidateName = payload.getCandidate().getName();
        String contactNo = payload.getCandidate().getPhones().get(0).toString();
        String email = payload.getCandidate().getEmails().get(0);
        Candidate candidate = new Candidate();
        candidate.setContactNo(Long.valueOf(contactNo));
        candidate.setName(candidateName);
        candidate.setEmail(email);
        candidate.setApplicationStatus("Application Under Process");
        repo.save(candidate);
    }

    @PostMapping("/applied")
    public CompletableFuture<String> candidateApplied(@RequestBody AppliedRecrutieeResponse recruiteeResponse) {
        //Important
        com.integration.recruitee.model.applyCandidate.Payload payload = recruiteeResponse.getPayload();
        if (payload != null) {
            String candidateName = payload.getCandidate().getName();
            String contactNo = payload.getCandidate().getPhones().get(0).toString();
            String appliedPosition = payload.getOffers().get(0).getTitle();
            String companyName = payload.getCompany().getName();
            String message = "Hi " + candidateName + "\n" +
                    "Thank you for your interest to work at " + companyName + " for the position " + appliedPosition + ". We have received your resume and our team will get back to you shortly.";
            callTwilioWhatsappAPI(contactNo, message);
            saveCandidate(payload);
        }
        return null;
    }

    private void callTwilioWhatsappAPI(String contactNo, String messageBody) {
        Map<String, String> bodyParam = new HashMap<>();
        bodyParam.put("message", messageBody);
        bodyParam.put("waId", contactNo);
        try {
            String requestBody = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(bodyParam);
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(NODE_API + "/sendMessage"))
                    .POST((HttpRequest.BodyPublishers.ofString(requestBody)))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            Message message = new ObjectMapper()
                    .readValue(json, Message.class);
            System.out.println("Message Sent Successfully, SID: " + message.getSid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Need to check
    private void collectFeedback(String contactNo) {
        Map<String, String> bodyParam = new HashMap<>();
        bodyParam.put("eventName", "collect_feedback");
        bodyParam.put("waId", contactNo);
        try {
            String requestBody = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(bodyParam);
            HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(NODE_API + "/eventTrigger"))
                .POST((HttpRequest.BodyPublishers.ofString(requestBody)))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
            HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            System.out.println("Feedback response: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
