package com.example.simplearticle.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(Object id) {
        super("Record not found with id: " + id);
    }
}
