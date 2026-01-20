package sn.fr.samagp.exceptions;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request, errors);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN, request, null);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request, null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT, request, null);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = "Operation violates data integrity";

        if (ex.getRootCause() instanceof ConstraintViolationException) {
            message = "Constraint violation: " + ex.getRootCause().getMessage();
        }

        return buildResponse(message, HttpStatus.CONFLICT, request, null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, String> errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));

        return buildResponse("Validation constraints violated", HttpStatus.BAD_REQUEST, request, errors);
    }



    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = String.format("Parameter '%s' should be of type %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"
        );
        return buildResponse(message, HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = "Malformed JSON request";

        if (ex.getCause() instanceof JsonParseException) {
            message = "Invalid JSON format: " + ex.getCause().getMessage();
        } else if (ex.getCause() instanceof MismatchedInputException) {
            message = "Invalid input: " + ex.getCause().getMessage();
        }

        return buildResponse(message, HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String message = String.format("Method %s not supported for this endpoint. Supported methods: %s",
                ex.getMethod(),
                String.join(", ", ex.getSupportedMethods() != null ? ex.getSupportedMethods() : new String[0])
        );
        return buildResponse(message, HttpStatus.METHOD_NOT_ALLOWED, request, null);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiError> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        String message = String.format("Media type %s not supported. Supported types: %s",
                ex.getContentType(),
                ex.getSupportedMediaTypes().stream()
                        .map(MediaType::toString)
                        .collect(Collectors.joining(", "))
        );
        return buildResponse(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request, null);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParams(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String message = String.format("Required parameter '%s' of type %s is missing",
                ex.getParameterName(),
                ex.getParameterType()
        );
        return buildResponse(message, HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        System.err.println("Unexpected error: " + ex.getMessage());
        ex.printStackTrace();
        return buildResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, request, ex.getMessage());
    }
    @ExceptionHandler(KeycloakServiceException.class)
    public ResponseEntity<ApiError> handleKeycloackService(KeycloakServiceException ex, HttpServletRequest request) {
        String message = String.format("Unexpected error occurred with file type: Error => %s", ex.getMessage());
        return buildResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, request, null);
    }
    @ExceptionHandler(SamaGPException.class)
    public ResponseEntity<ApiError> handleSamaGPException(SamaGPException ex, HttpServletRequest request) {
        String message = String.format("Unexpected error occurred with file type: Error => %s", ex.getMessage());
        return buildResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, request, null);
    }
    @ExceptionHandler(StripePaymentException.class)
    public ResponseEntity<ApiError> handleStripePayment(StripePaymentException ex, HttpServletRequest request) {
        String message = String.format("Unexpected error occurred with file type: Error => %s", ex.getMessage());
        return buildResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, request, null);
    }
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiError> handleFileStorage(FileStorageException ex, HttpServletRequest request) {
        String message = String.format("Unexpected error occurred with file type: Error => %s", ex.getMessage());
        return buildResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, request, null);
    }

    @ExceptionHandler(PlanAbonnementAlreadyExistsException.class)
    public ResponseEntity<ApiError> handlePlanAlreadyExists(
            PlanAbonnementAlreadyExistsException ex, HttpServletRequest request
    ) {
        String message = String.format("Plan already exist, Error: %s", ex.getMessage());

        return buildResponse(message, HttpStatus.CONFLICT, request, null);
    }

    private ResponseEntity<ApiError> buildResponse(String message, HttpStatus status, HttpServletRequest request, Object details) {
        ApiError error = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                LocalDateTime.now(),
                details
        );
        return new ResponseEntity<>(error, status);
    }

}
