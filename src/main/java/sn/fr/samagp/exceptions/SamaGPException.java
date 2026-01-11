package sn.fr.samagp.exceptions;

public class SamaGPException extends RuntimeException {

    SamaGPException (String message, Throwable throwable) {
        super(message, throwable);
    }
}
