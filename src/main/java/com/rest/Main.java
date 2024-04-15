package com.rest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;


import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class Main {
/* public static void main(String[] args) throws InterruptedException {




       // System.setProperty("javax.net.debug", "all");

        List<String> responsetypes =List.of("application/json","text/plain","application/xml","text/html");
        GenericRestClient<String> client = new GenericRestClient<>();




    for (String responsetype : responsetypes)
        {
            System.out.println("\n***************************\n");
            MessageEnvelope<String> envelope2 = new MessageEnvelope.Builder<String>("/hello",HttpMethod.GET).accept(responsetype)
                .build();
            Response response3 = client.callRestApi(envelope2);
            String responseBody3 = client.processResponse(response3, String.class);
            System.out.println("Response Type : "+responsetype);
            System.out.println("Response body  : \n "+responseBody3);
            Thread.currentThread().sleep(1000);
        }




     List<Map.Entry<String, String>> entries = new java.util.ArrayList<>();

     entries.add(new AbstractMap.SimpleEntry<>("id", "1"));
     MessageEnvelope<String> envelope = new MessageEnvelope.Builder<String>("/api/data",HttpMethod.GET).accept(MediaType.APPLICATION_JSON)
             .queryParams(entries)
             .build();
     Response response = client.callRestApi(envelope);
     String responseBody = client.processResponse(response, String.class);
     System.out.println("Response  : "+responseBody);





     List<Map.Entry<String, String>> entriesApi = new java.util.ArrayList<>();
     entriesApi.add(new AbstractMap.SimpleEntry<>("country", "us"));
     MessageEnvelope<String> envelopeAPI = new MessageEnvelope.Builder<String>("",HttpMethod.GET).accept(MediaType.APPLICATION_JSON)
             .queryParams(entriesApi)
             .build();
     Response responseAPI = client.callRestApi(envelopeAPI);
     String responseBodyAPI = client.processResponse(responseAPI, String.class);
     System.out.println("Response  : "+responseBodyAPI);


     client.close();



 }*/

/*    public static void main(String[] args) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {


        System.setProperty("javax.net.debug", "all");
        SSLContext sslContext = SslConfigurator.newInstance()
                .trustStoreFile("../client.truststore")
                .trustStorePassword("password")
                .keyStoreFile("../client.jks")
                .keyPassword("password")
                .createSSLContext();

        // Set SSLContext on ClientBuilder
        Client client =JerseyClientBuilder.newBuilder()
                .sslContext(sslContext)
                .hostnameVerifier((hostname, session) -> true) // Disable hostname verification
                .build();

       Response response = client.target("https://localhost:9090/names").request().get();
        System.out.println(response);
        System.out.println("Respone is "+response.readEntity(String.class));
    }*/

    /* public static void main(String[] args) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {


         // Create a Jersey client
         Client client = JerseyClientBuilder.newClient();

         ClientConfig config = new ClientConfig();
         HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
                 .credentials("myclient", "qyrUq111K5FPUJpF6Wl71S3aIoh5IWcv")
                 .build();
         config.register(feature);

         // Obtain an access token (manually construct the token request)
         Response tokenResponse = client.target("http://localhost:8080/realms/myrealm/protocol/openid-connect/token")
                 .queryParam("grant_type", "client_credentials")
                 .request(MediaType.APPLICATION_JSON)
                 .post(null);

         if (tokenResponse.getStatus() == 200) {
             String accessToken = tokenResponse.readEntity(String.class);
             System.out.println("Access Token: " + accessToken);

             // Make an authenticated request
             Response response = client.target("localhost:9091/api/public")
                     .request()
                     .header("Authorization", "Bearer " + accessToken)
                     .get();

             if (response.getStatus() == 200) {
                 String responseData = response.readEntity(String.class);
                 System.out.println("Response: " + responseData);
             } else {
                 System.err.println("Error fetching data. Status code: " + response.getStatus());
             }
         }
         else {
             System.err.println("Error obtaining access token. Status code: " + tokenResponse.getStatus());
         }


     }*/


    public static void main(String[] args) throws InterruptedException {




        System.setProperty("javax.net.debug", "all");
        GenericRestClient<String> client = new GenericRestClient<>();



            System.out.println("\n***************************\n");
            MessageEnvelope<String> envelope2 = new MessageEnvelope.Builder<String>("/api/public",HttpMethod.GET).accept(MediaType.APPLICATION_JSON)
                    .build();
            Response response3 = client.callRestApi(envelope2);
            String responseBody3 = client.processResponse(response3, String.class);
             System.out.println("Response body  : \n "+responseBody3);
            Thread.currentThread().sleep(1000);


           client.close();



    }



}