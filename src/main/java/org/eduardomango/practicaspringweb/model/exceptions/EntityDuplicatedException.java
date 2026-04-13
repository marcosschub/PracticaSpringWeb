package org.eduardomango.practicaspringweb.model.exceptions;

public class EntityDuplicatedException extends RuntimeException {
    public EntityDuplicatedException(String message) {
        super(message);
    }

    public EntityDuplicatedException() {
    }
}
