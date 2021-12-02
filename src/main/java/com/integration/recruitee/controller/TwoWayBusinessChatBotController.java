package com.integration.recruitee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.recruitee.model.OfferResponse;
import com.integration.recruitee.model.Offers;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
public class TwoWayBusinessChatBotController {
    final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
    final String TWILIO_SANDBOX_NUMBER = "whatsapp:+14155238886";

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
                .uri(URI.create("https://ideas2ittechnologies.recruitee.com/api/offers/"))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        OfferResponse offerResponse = new ObjectMapper()
                .readValue(json, OfferResponse.class);
        List<Offers> offersList = offerResponse
                .getOffers()
                .stream()
                .collect(Collectors.toList());
        //jobRoles contain slug & job title
        Map<String, String> jobRoles = offersList
                .stream()
                .collect(Collectors.toMap(Offers::getSlug, Offers::getTitle));
        //after choosing from whatsapp reply we can chose slug in this map.
        return jobRoles;
    }

    /**
     * new candidate creation
     *
     * @return Response from Recuruitee
     * @throws IOException          Exception
     * @throws InterruptedException Exception
     */
    private CompletableFuture<String> createCandidate() throws IOException, InterruptedException {
        //FIXME
        //receiving data from whatsapp response.(As of now values are hardcoded)
        //RequestBody
        //FIXME
        String slug = viewOffers().get("Business Analyst");
        Map<String, String> bodyParam = new HashMap<>();
        bodyParam.put("name", "sample2");
        bodyParam.put("email", "sample@gmail.com");
        bodyParam.put("phone", "1234567890");
        bodyParam.put("remote_cv_url", "C:\\Users\\Kowshik Bharathi M\\Desktop\\samplepdf");

        String requestBody = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(bodyParam);

        // Create HTTP request object
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://ideas2ittechnologies.recruitee.com/api/offers/" + slug + "/candidates"))
                .POST((HttpRequest.BodyPublishers.ofString(requestBody)))
                .header("accept", "application/json")
                .build();
        return HttpClient
                .newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
