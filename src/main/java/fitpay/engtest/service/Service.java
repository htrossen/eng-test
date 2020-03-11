package fitpay.engtest.service;

import fitpay.engtest.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service: Supports Controller to provide client requested data.
 */
@org.springframework.stereotype.Service
public class Service {

    private String authToken;

    /**
     * Build authentication rest template with client id and secret
     *
     * @param builder (RestTemplateBuilder)
     * @return RestTemplate authRestTemplate
     */
    @Bean
    public RestTemplate authRestTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthorization("XgqiDz1G", "VCZlcph3").build();
    }

    /**
     * Get Bearer token for Api calls
     *
     * If authentication credentials are invalid or if token cannot be parsed throw exception
     * -  Unable to parse Bearer token: 401 Unauthorized
     * -  Unable to parse Bearer token: null
     */
    @Bean
    @Qualifier("authRestTemplate")
    public CommandLineRunner run(RestTemplate restTemplate) {
        String authUrl = "https://auth.qa.fitpay.ninja/oauth/token?grant_type=client_credentials";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return args -> {
            try {
                Token token = restTemplate.exchange(authUrl, HttpMethod.GET, entity, Token.class).getBody();
                if (token != null) {
                    authToken = token.getAccess_token();
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new Exception("Unable to parse Bearer token: " + e.getMessage());
            }
        };
    }

    /**
     * Base case: Get unfiltered credit card and device info for given user
     *
     * @param userId (String)
     * @return Response object containing userId, creditCards, and devices
     */
    public Response getUserInfo(String userId) {
        Response response = new Response();
        response.setUserId(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.qa.fitpay.ninja/users/";
        CreditCards cards = restTemplate.exchange(baseUrl + userId + "/creditCards", HttpMethod.GET, entity, CreditCards.class).getBody();
        if (cards != null) {
            response.setCreditCards(cards.getCards());
        } else {
            response.setCreditCards(Collections.emptyList());
        }

        Devices devices = restTemplate.exchange(baseUrl + userId + "/devices", HttpMethod.GET, entity, Devices.class).getBody();
        if (devices != null) {
            response.setDevices(devices.getDevices());
        } else {
            response.setDevices(Collections.emptyList());
        }

        return response;
    }

    /**
     * Filter base case to only include credit cards with given creditCardState
     *
     * @param response (Response: base case)
     * @param creditCardState (String: desired credit card state filter)
     * @return Response object containing userId, creditCards (filtered), and devices
     */
    public Response getUserInfoCardFilter(Response response, String creditCardState) {
        List<CreditCard> filteredCards = new ArrayList<>();

        for (int c=0; c < response.getCreditCards().size(); c=c+1) {
            if (response.getCreditCards().get(c).getState().equals(creditCardState)) {
                filteredCards.add(response.getCreditCards().get(c));
            }
        }
        response.setCreditCards(filteredCards);
        return response;
    }

    /**
     * Filter base case to only include devices with give deviceState
     *
     * @param response (Response: base case)
     * @param deviceState (String: desired device state filter)
     * @return Response object containing userId, creditCards, and devices (filtered)
     */
    public Response getUserInfoDeviceFilter(Response response, String deviceState) {
        List<Device> filteredDevices = new ArrayList<>();

        for (int d=0; d < response.getDevices().size(); d=d+1) {
            if (response.getDevices().get(d).getState().equals(deviceState)) {
                filteredDevices.add(response.getDevices().get(d));
            }
        }
        response.setDevices(filteredDevices);
        return response;
    }

    /**
     * Filter base case to only include credit cards with given creditCardState and devices with give deviceState
     *
     * @param response (Response: base case)
     * @param creditCardState (String: desired credit card state filter)
     * @param deviceState (String: desired device state filter)
     * @return Response object containing userId, creditCards (filtered), and devices (filtered)
     */
    public Response getUserInfoCardDeviceFilter(Response response, String creditCardState, String deviceState) {

        response = getUserInfoCardFilter(response, creditCardState);
        response = getUserInfoDeviceFilter(response, deviceState);
        return response;
    }
}


