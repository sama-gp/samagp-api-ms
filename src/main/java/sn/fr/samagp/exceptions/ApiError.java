package sn.fr.samagp.exceptions;

import java.time.LocalDateTime;

public record ApiError(int status,
                       String error,
                       String message,
                       String path,
                       LocalDateTime timestamp,
                       Object details) {
}
