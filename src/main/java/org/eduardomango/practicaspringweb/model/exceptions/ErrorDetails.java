package org.eduardomango.practicaspringweb.model.exceptions;

import lombok.*;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ErrorDetails {
    //todo esta bien esto??
    private final LocalTime time = LocalTime.now();
    private final String message;
    private final String endpoint;


}
