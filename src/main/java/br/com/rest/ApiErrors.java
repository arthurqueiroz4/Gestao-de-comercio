package br.com.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(List<String> erros){
        this.errors = erros;
    }


    public ApiErrors(String msg){
        this.errors = Arrays.asList(msg);
    }

}
