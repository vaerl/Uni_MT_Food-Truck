package de.thm.foodtruckbe.entities.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6635879409469826778L;

    public EntityNotFoundException(String entity, Long id) {
        super("Entity " + entity + " with id " + id + " does not exist.");
    }

}