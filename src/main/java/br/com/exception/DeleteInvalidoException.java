package br.com.exception;

public class DeleteInvalidoException extends RuntimeException{
    public DeleteInvalidoException(String message) {
        super(message);
    }
}
