package com.rest;

public enum AuthenticationType {
    NONE,
    BASIC,

    MTLS,

    QUERYKEY,

    API_KEY,
    OAUTH2_CLIENTCREDENTIALS;
}