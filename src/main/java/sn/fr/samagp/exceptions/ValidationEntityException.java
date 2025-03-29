package sn.fr.samagp.exceptions;

import java.util.List;

public class ValidationEntityException extends RuntimeException {
    private final List<String> errorMessages;

    public ValidationEntityException(List<String> errorMessages) {
        super("Erreur de validation des données");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}