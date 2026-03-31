package org.eduardomango.practicaspringweb.model.exceptions;

public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException(String message) {
        super(message);
    }
    public SaleNotFoundException(){}
}
