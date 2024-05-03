package org.example.cafeflow.exception;

public class BoardIntegrityViolationException extends RuntimeException {
    public BoardIntegrityViolationException(String message) {
        super(message);
    }
}