package com.lyubendimitrov.gifapp.web;

import lombok.Value;

@Value
public class ValidationMessage {
    String message;
    Status status;

    public static enum Status {
        SUCCESS, FAILURE
    }
}
