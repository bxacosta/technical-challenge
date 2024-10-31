package com.bxacosta.clientservice.utils;

import com.bxacosta.clientservice.entities.Client;

public final class ClientUtils {

    private ClientUtils() {
    }

    public static void validate(Client client) {
        ValidationUtils.validateRequiredField(client.getName(), "name");
    }
}
