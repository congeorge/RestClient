package com.rest;

import java.util.concurrent.TimeUnit;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.Feature;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;

import javax.net.ssl.SSLContext;

public class RestClientFactory {
    private static final int DEFAULT_CONNECTION_TIMEOUT_SECONDS = 10;
    private static final int DEFAULT_READ_TIMEOUT_SECONDS = 30;

    private RestClientFactory(){}




    public static Client createClient() {
        AuthenticationType authType = AuthenticationType.valueOf(AppConfig.getProperty("authentication.strategy").toUpperCase());
        int connectionTimeout = Integer.parseInt(AppConfig.getProperty("connection.timeout.seconds", String.valueOf(DEFAULT_CONNECTION_TIMEOUT_SECONDS)));
        int readTimeout = Integer.parseInt(AppConfig.getProperty("read.timeout.seconds", String.valueOf(DEFAULT_READ_TIMEOUT_SECONDS)));
        return createClient(authType, connectionTimeout, readTimeout);
    }

    private static Client createClient(AuthenticationType authType, int connectionTimeout, int readTimeout) {
        ClientBuilder builder = ClientBuilder.newBuilder()
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS);

        switch (authType) {
            case BASIC:
                return createClientWithBasicAuth(builder);
            case MTLS:
                return createClientWithMTLS(builder);
            case API_KEY:
                return createClientWithApiKey(builder);
            case OAUTH2_CLIENTCREDENTIALS:
                return createClientWithClientCredentials(builder);
            default:
                throw new IllegalArgumentException("Unsupported authentication type: " + authType);
        }
    }

    private static Client createClientWithClientCredentials(ClientBuilder builder) {
        String accessToken = AccessTokenProvider.getAccessToken();
        Feature feature = OAuth2ClientSupport.feature(accessToken);
        return builder.register(feature).build();

    }

    private static Client createClientWithBasicAuth(ClientBuilder builder) {
        String username = AppConfig.getProperty("username");
        String password = AppConfig.getProperty("password");
        return builder.register(HttpAuthenticationFeature.basic(username, password))
                .build();
    }

    private static Client createClientWithMTLS(ClientBuilder builder) {
        String truststorePath = AppConfig.getProperty("truststore.path");
        String truststorePassword = AppConfig.getProperty("truststore.password");
        String clientKeystorePath = AppConfig.getProperty("client.keystore.path");
        String clientKeystorePassword = AppConfig.getProperty("client.keystore.password");

         SSLContext sslContext = SslConfigurator.newInstance()
                .trustStoreFile(truststorePath)
                .trustStorePassword(truststorePassword)
                .keyStoreFile(clientKeystorePath)
                .keyPassword(clientKeystorePassword)
                .createSSLContext();
        try {
            return builder.sslContext(sslContext)
                    .hostnameVerifier((hostname, session) -> true) // Disable hostname verification
                    .build();
        }catch (Exception e) { e.printStackTrace();}


        return null;
    }

    private static Client createClientWithApiKey(ClientBuilder builder) {
        String apiKey = AppConfig.getProperty("api_key");
        // Create a Jakarta client with API key
        return builder.register((ClientRequestFilter) requestContext -> requestContext.getHeaders().add("x-api-key", apiKey))
                .build();


  /*      1c5cd47231064880a54cf4d7a3226519


        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("https://api.example.com/path/to/resource")
                .queryParam("apikey", API_KEY);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String responseData = response.readEntity(String.class);
            System.out.println("Response: " + responseData);
        } else {
            System.err.println("Error: HTTP " + response.getStatus() + ", " + response.getStatusInfo());
        }

        response.close();
        client.close()*/
    }
}
