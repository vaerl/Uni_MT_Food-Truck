package de.thm.foodtruckbe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6635879409469826778L;

    public EntityNotFoundException(String entity, Long id) {
        super("Entity " + entity + " with id " + id + " does not exist.");
    }

    public EntityNotFoundException(String entity, String name) {
        super("Entity " + entity + " with id " + name + " does not exist.");
    }

}