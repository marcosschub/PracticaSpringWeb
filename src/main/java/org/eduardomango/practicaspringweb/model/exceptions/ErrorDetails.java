package org.eduardomango.practicaspringweb.model.exceptions;

import java.sql.Timestamp;
import java.time.LocalTime;

public class ErrorDetails {
    private LocalTime time;
    private String message;
    private String endpoint;

    public ErrorDetails(String message, String endpoint) {
        this.time = LocalTime.now();
        this.message = message;
        this.endpoint = endpoint;
    }


}
