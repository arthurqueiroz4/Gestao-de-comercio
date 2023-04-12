package br.com.rest;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(String msg){
        this.errors = Arrays.asList(msg);
    }

}
