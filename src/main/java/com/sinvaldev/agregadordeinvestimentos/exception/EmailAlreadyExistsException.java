package com.sinvaldev.agregadordeinvestimentos.exception;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException() {super("Email já cadastrado"); }
}
