package br.com.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
    public NotFoundException(String msg){
        super(msg);
    }
}
