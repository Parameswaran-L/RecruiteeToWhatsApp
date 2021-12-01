package com.integration.recruitee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.recruitee.model.OfferResponse;
import com.integration.recruitee.model.Offers;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Media;
import com.twilio.twiml.messaging.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chatbot")
public class TwoWayBusinessChatBotController {
    final String ACCOUNT_SID = "AC441d5adf730e937ea1a895fa490449d9";
    final String AUTH_TOKEN = "d80a19dd7e0435fa59184490d399e880";
    final String TWILIO_SANDBOX_NUMBER = "whatsapp:+14155238886";

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String sendWelcomeMessage() throws IOException, InterruptedException {
        Map<String, String> offersMap = viewOffers();
        StringBuilder jobOpeningsStringBuilder = new StringBuilder();

        if (!offersMap.isEmpty()) {
            int jobId = 1;

            for (Map.Entry<String, String> entry : offersMap.entrySet()) {
                jobOpeningsStringBuilder.append("Welcome to Ideas2It! \n Want to be Part of our great team, We're hiring for \n");
                jobOpeningsStringBuilder.append(jobId++);
                jobOpeningsStringBuilder.append(". ");
                jobOpeningsStringBuilder.append(entry.getValue());
                jobOpeningsStringBuilder.append("\n");
            }
        } else {
            jobOpeningsStringBuilder.append("Thanks for Reaching to us, Presently there are no openings - Ideas2it HR Team");
        }

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
    }

    /**
     * Available position in organization.
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
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
     * @return
     * @throws IOException
     * @throws InterruptedException
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
