package br.com.rest.controller;

import br.com.exception.BadRequestException;
import br.com.exception.DeleteInvalidoException;
import br.com.exception.NotFoundException;
import br.com.exception.SenhaInvalidaException;
import br.com.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
//    @ExceptionHandler(RegraNegocioException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiErrors handleException(RegraNegocioException ex){
//        String erro = ex.getMessage();
//        return new ApiErrors(erro);
//
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors haandleMethodNotValidException(MethodArgumentNotValidException ex){
        List<String> erros = ex.getBindingResult().getAllErrors()
                .stream().map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(erros);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleNotFoundException(NotFoundException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBadRequestException(BadRequestException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(DeleteInvalidoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrors handleDeleteInvalidoException(DeleteInvalidoException ex){
        return new ApiErrors(ex.getMessage());
    }
    @ExceptionHandler(SenhaInvalidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handSenhaInvalidaException(SenhaInvalidaException ex){
        return new ApiErrors(ex.getMessage());
    }
}
