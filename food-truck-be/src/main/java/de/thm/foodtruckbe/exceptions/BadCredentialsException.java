package de.thm.foodtruckbe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadCredentialsException  extends RuntimeException{

    public BadCredentialsException(){
        super("Wrong username or password.");
    }
}
