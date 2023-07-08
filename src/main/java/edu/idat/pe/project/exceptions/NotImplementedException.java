package edu.idat.pe.project.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotImplementedException extends RuntimeException {

    private String message;


    public NotImplementedException(String message) {
        super();
        this.message = message;
    }
}

