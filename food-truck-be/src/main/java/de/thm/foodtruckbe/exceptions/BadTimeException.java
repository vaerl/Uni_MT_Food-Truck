package de.thm.foodtruckbe.exceptions;

public class BadTimeException extends RuntimeException {

    private static final long serialVersionUID = 9108391332892007148L;

    public BadTimeException(String message) {
        super(message);
    }

}