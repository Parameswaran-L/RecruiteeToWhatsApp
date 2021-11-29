package com.integration.recruitee.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.recruitee.model.Payload;
import com.integration.recruitee.model.RecrutieeResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/integration")
public class IntegrationController {
	
    @GetMapping("/api")
    public CompletableFuture<String> integrationByRecrutiee(@RequestBody RecrutieeResponse recrutieeResponse) throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        
        //Important
        Payload payload = recrutieeResponse.payload;
        String ToPipeLine = payload.details.toStage.name;
		String candidateName = payload.getCandidate().name;
		String contactNo = payload.getCandidate().phones.get(0).toString();
		String appliedPosition = payload.getOffer().title;
		String companyName = payload.getCompany().name;
		String message = "";
        switch(ToPipeLine){
			case "Applied":
				message = " Hi" + candidateName +  "\n" +
			"Thank you for your interest to work at" + companyName + "for the position "+ appliedPosition +". We have received your resume and our team will get back to you shortly.";
				//seperate method for sanbox
				break;
			case "Discovery- Justin C":
				//message = "Hi"+ candidateName + "you"
				break;
			case "Ideas2IT - Intro":
				message = " Hi" + candidateName +  "\n" +
						" Ideas2IT is a great place to grow your career. You get the opportunity to chart your own career path, where you could choose to be a technical expert, entrepreneur, or even an entrepreneu. Please find JD"+ " https://ideas2ittechnologies.recruitee.com/o/";
				break;
			case "Ideas2IT | L1 Interview":
				message = "Hi"+ candidateName +"!!!"+ "\n" + "Your Profile has been shortlisted for L1 interview process. Google meet invitation have sent to your register mail id";
				break;
			case "Final Interview":
                  message = "Hi"+ candidateName + "\n" +"!!! Your have cleared L1 interview.";
				break;
			case "Rejection":
				message =
						"Hi"+ candidateName +"\n" +"Thank you for taking the time to meet with our team about the Java role at Ideas2IT. It was a pleasure to learn more about your skills and accomplishments.\n" +
						"\n" +
						"Unfortunately, our team did not select you for further consideration. Often there are some special needs for a particular role that influences this decision.";
				break;

			case "HR Discussion":
				break;
			case "Document Collection":
				message = "Hi"+ candidateName+ "."+"\n" +
						"\n" +
						"Congratulations!\n" +
						"\n" +
						"We have been impressed with your Interview background and Ideas2IT would like to formally offer you.Please share us required document which is mention mail sent to your registered mail id";
				break;
			case "Onboarded":
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
				break;


		}
/**
        Map<String, String> campaginMap = new HashMap<String, String>()
        {{
             put("Ideas2IT: Immediate Joiners", "cam_uvYa6TGqt3JSRbyPs");
             put("Ideas2IT: DOJ in 1 month", "cam_Ko8yJWBrtLgqeFEod");
             put("Ideas2IT: DOJ in 2 month", "cam_C6TEDMeZMGPYxzBgk");
             put("Ideas2IT: DOJ in 3 month", "cam_qyJDT27AGJThBe2ME");
             put("Ideas2IT: DOJ in 15 days", "cam_Ko8yJWBrtLgqeFEod");
        }};
        
    	   //For Basic Authentication
    	   String auth =  ":f0777b800f62ebaf6e57b03d8ebb1887";
    	   byte[] encodedAuth = Base64.encodeBase64(auth.getBytes("UTF-8"));
    	   String authHeaderValue = "Basic " + new String(encodedAuth);
    	   String email = payload.getCandidate().emails.get(0);
        
      if(campaginMap.containsKey(ToPipeLine)) {
    	   //API url formation
    	   String url = "https://api.lemlist.com/api/campaigns/"+campaginMap.get(payload.details.toStage.name)+"/leads/"+ email;
    	   URI uri = new URI(url);       
    	   ObjectMapper objectMapper = new ObjectMapper();
    	   //RequestBody
    	   Map<String,String>map=new HashMap<>();
    	   map.put("firstName", payload.getCandidate().name);
    	   map.put("companyName", payload.getCompany().name);
    	   String requestBody = objectMapper
              .writerWithDefaultPrettyPrinter()
              .writeValueAsString(map);

    	   // Create HTTP request object
    	   HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST((HttpRequest.BodyPublishers.ofString(requestBody)))
                .header("Authorization", authHeaderValue)
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .build();
    	   // Send HTTP request
    	   return  HttpClient.newHttpClient()
    			   .sendAsync(request, HttpResponse.BodyHandlers.ofString())
    			   .thenApply(HttpResponse::body);		   	   
      }
      if(ToPipeLine.equals("Hired")) {
    	   String url = "https://timesheet.ideas2it.com/api/on-boarding/recruitee";
    	   URI uri = new URI(url);       
    	   ObjectMapper objectMapper = new ObjectMapper();
    	   //RequestBody
    	   Map<String,String>map=new HashMap<>();
    	   map.put("name", payload.getCandidate().name);
    	   map.put("email", email);
    	   String requestBody = objectMapper
              .writerWithDefaultPrettyPrinter()
              .writeValueAsString(map);

    	   // Create HTTP request object
    	   HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST((HttpRequest.BodyPublishers.ofString(requestBody)))
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .build();
    	   // Send HTTP request
    	   return  HttpClient.newHttpClient()
    			   .sendAsync(request, HttpResponse.BodyHandlers.ofString())
    			   .thenApply(HttpResponse::body);	   
       }
 **/
       return null;
  }
}
