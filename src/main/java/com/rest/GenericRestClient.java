package com.rest;


import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Response;


public class GenericRestClient<T> {

    private static final String SERVER_BASE_URL=AppConfig.getProperty("server.url");
    private static final Logger LOGGER = Logger.getLogger(GenericRestClient.class.getName());

    private static final Client client = RestClientFactory.createClient();


    public Response callRestApi(MessageEnvelope envelope) {


        // Create a Jakarta client
        try  {

            String completeUrl =  SERVER_BASE_URL+ envelope.getPath();

            // Build the target URL
            WebTarget target = client.target(completeUrl);

            // Add query parameters for GET request
            if ((envelope.getMethod() == HttpMethod.GET) && (envelope.getQueryParams() != null)) {
                List<Map.Entry<String, String>> paramlist = envelope.getQueryParams();

                for (Map.Entry<String, String> entry : paramlist) {
                    target = target.queryParam(entry.getKey(), entry.getValue());
                    //
                    System.out.println("Query"+ entry.getKey());

                    System.out.println("Query value "+entry.getValue());
                }
            }
            System.out.println("Target :" +target.getUri());

            // Build the request
            Invocation.Builder requestBuilder = target.request();

            // Add custom headers
     /*       if (envelope.getHeaders() != null) {
                envelope.getHeaders().forEach(requestBuilder::header);
            }*/

            if (envelope.getAccept() != null) {
                requestBuilder = requestBuilder.accept(envelope.getAccept());
            }

            System.out.println("Contenttype : "+envelope.getAccept());

            // Call the API
            Response response = null;

            switch (envelope.getMethod()) {
                case GET:
                    response = requestBuilder.get();

                    break;

                case POST:
                    response = requestBuilder.post(Entity.entity(envelope.getPayload(), envelope.getContentType()));

                    break;

                // Add support for other HTTP methods as needed
            }

            return response;
        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "Error while calling REST API", e);
            return null;
        }
    }


    public  void close() {
        client.close();
    }

    public T processResponse(Response response, Class<T> responseType) {

        if (response != null) {
            int status = response.getStatus();

            LOGGER.info("Response status: {0}" + status);

            if (status != Response.Status.OK.getStatusCode()) {
                 LOGGER.severe("Error response: " + response.getStatusInfo().getReasonPhrase());
            } else {
                return response.readEntity(responseType);
            }
        } else {
            // Log error message if response is null
            LOGGER.severe("No response received");
        }

        return null;
    }

}

