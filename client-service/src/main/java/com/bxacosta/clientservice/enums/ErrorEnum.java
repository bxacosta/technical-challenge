package com.bxacosta.clientservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {

    CLIENT_NOT_FOUND("Client not found");

    private final String message;
}
