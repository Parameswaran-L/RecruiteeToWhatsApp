package com.integration.recruitee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.recruitee.model.feedback.Candidate;
import com.integration.recruitee.model.feedback.FeedBack;
import com.integration.recruitee.model.applyCandidate.CreateCandidate;
import com.integration.recruitee.model.offerResponse.OfferResponse;
import com.integration.recruitee.model.offerResponse.Offers;
import com.integration.recruitee.repository.CandidateRepository;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
public class TwoWayBusinessChatBotController {

    @Autowired
    private CandidateRepository repo;

    @PostMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String sendWelcomeMessage(@RequestParam Map<String, String> requestParameters) throws IOException, InterruptedException {
        Logger logger = Logger.getLogger(TwoWayBusinessChatBotController.class.getName());

        requestParameters.forEach((k, v) -> logger.info("Key = " + k + ", Value = " + v));
        StringBuilder jobOpeningsStringBuilder = new StringBuilder();

        switch (requestParameters.get("Body")) {

            case "Hey": {
                jobOpeningsStringBuilder.append("Hey there! We are excited as you are! \n");
                break;
            }
            default: {
                Map<String, String> offersMap = viewOffers();

                if (!offersMap.isEmpty()) {
                    int jobId = 1;
                    jobOpeningsStringBuilder.append("Welcome to Ideas2It! \nWant to be Part of our great team, We're hiring for \n");
                    String firstPosition = null;
                    for (Map.Entry<String, String> entry : offersMap.entrySet()) {
                        if (jobId == 1) {
                            firstPosition = entry.getValue();
                        }
                        jobOpeningsStringBuilder.append(jobId++);
                        jobOpeningsStringBuilder.append(". ");
                        jobOpeningsStringBuilder.append(entry.getValue());
                        jobOpeningsStringBuilder.append("\n");
                    }
                    jobOpeningsStringBuilder.append("\nReply with your option to apply \n\nExample : Reply 1 to apply for ")
                            .append(firstPosition);
                } else {
                    jobOpeningsStringBuilder.append("Thanks for Reaching to us, Presently there are no openings - Ideas2it HR Team");
                }
            }
        }
        if (jobOpeningsStringBuilder != null) {
            Body body = new Body
                    .Builder(jobOpeningsStringBuilder.toString())
                    .build();
            Message whatsappMessage = new Message
                    .Builder()
                    .body(body)
                    .build();
            MessagingResponse twiml = new MessagingResponse
                    .Builder()
                    .message(whatsappMessage)
                    .build();

            return twiml.toXml();
        } else
            return null;
    }

    /**
     * Available position in organization.
     *
     * @return Return a list of Offers as in MAP
     * @throws IOException          Exception
     * @throws InterruptedException Exception
     */
    //FIXME
    private Map<String, String> viewOffers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://ideas2ithackathon.recruitee.com/api/offers/"))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        OfferResponse offerResponse = new ObjectMapper()
                .readValue(json, OfferResponse.class);
        List<Offers> offersList = new ArrayList<>(offerResponse
                .getOffers());
        //jobRoles contain slug & job title
        //after choosing from whatsapp reply we can chose slug in this map.
        return offersList
                .stream()
                .collect(Collectors.toMap(Offers::getSlug, Offers::getTitle));
    }

    /**
     * new candidate creation
     *
     * @return Response from Recuruitee
     * @throws IOException          Exception
     */
    @RequestMapping("/createcandidate")
    public CompletableFuture<String> createCandidate(@RequestBody CreateCandidate createCandidate) throws IOException {
        String slug =  createCandidate.getSlug() ;
        System.out.println("Create Candidate ===>"+ slug);
        Map<String, Map<String, String>> req = new HashMap<>();
        req.put("candidate", createCandidate.getCandidate());
        String requestBody = new ObjectMapper()
                .writeValueAsString(req);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ideas2ithackathon.recruitee.com/api/offers/java-developer/candidates"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        System.out.println(requestBody);
        return HttpClient
                .newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    //Need to check
    @PostMapping("/collectfeedback")
    public String collectFeedback(@RequestBody FeedBack feedback){
        Long contactNo = Long.valueOf(feedback.getPhone());
        String rating = feedback.getRating();
        String review = feedback.getReview();
        Candidate existingCandidate = repo.findCandidateByContactNo(contactNo);
        existingCandidate.setRating(rating);
        existingCandidate.setReview(review);
        repo.save(existingCandidate);
        return "Updated FeedBack Successfully";
    }
}
