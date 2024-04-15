package com.rest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.JerseyClientBuilder;


public class OAuth2Client {

    private static final String CLIENT_ID = "myclient";
    private static final String CLIENT_SECRET = "qyrUq111K5FPUJpF6Wl71S3aIoh5IWcv";
    private static final String TOKEN_ENDPOINT = "http://localhost:8080/realms/myrealm/protocol/openid-connect/token"; // Your token endpoint URL
    private static final String API_ENDPOINT = "http://localhost:9091/api/public"; // Your API endpoint URL

    public static void main(String[] args) {
        // Get OAuth2 Access Token
        String accessToken = getAccessToken();

        // Use the access token to access protected resources
        if (accessToken != null) {
            // Make API request
            Client client = JerseyClientBuilder.newClient();
            String response = client.target(API_ENDPOINT)
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + accessToken)
                    .get(String.class);

            // Handle API response
            System.out.println("API Response: " + response);
        } else {
            System.out.println("Failed to obtain access token.");
        }
    }

    private static String getAccessToken() {
        // Prepare form data for token request
        Form form = new Form();
        form.param("grant_type", "client_credentials");
        form.param("client_id", CLIENT_ID);
        form.param("client_secret", CLIENT_SECRET);

        // Send token request
        Client client = ClientBuilder.newClient();
        Invocation.Builder invocationBuilder = client.target(TOKEN_ENDPOINT)
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.form(form));

        // Check response status
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // Parse response and extract access token
            String jsonResponse = response.readEntity(String.class);
            // Assuming the response is in JSON format like: {"access_token":"your_access_token","token_type":"bearer","expires_in":3600}
            String accessToken = jsonResponse.split("\"access_token\":\"")[1].split("\"")[0];
            System.out.println("Access Token :"+jsonResponse);
            return accessToken;
        } else {
            System.out.println("Failed to obtain access token. Response code: " + response.getStatus());
            return null;
        }
    }
}

