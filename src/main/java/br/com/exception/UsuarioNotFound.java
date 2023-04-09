package br.com.exception;

public class UsuarioNotFound extends RuntimeException{
    public UsuarioNotFound(){
        super("Usuário não encontrado.");
    }
}
