package com.nequi.franchise.config;

import com.nequi.franchise.exceptions.ApiException;
import com.nequi.franchise.util.UtilExceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LogManager.getLogger();

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<String> handleUnknownException(Exception e, HttpServletRequest request) {
        logger.error(String.format("Internal error: request_uri: %s request_method: %s", request.getRequestURI(), request.getMethod()), e);
        Throwable t = UtilExceptions.getFromChain(e, ApiException.class);
        ApiException var10000;
        if (t instanceof ApiException a) {
            var10000 = a;
        } else {
            var10000 = new ApiException("internal_error", "Internal server error", 500);
        }

        ApiException apiException = var10000;
        return ResponseEntity.status(apiException.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(apiException.toJson());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationMethodArgument(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiException apiException = new ApiException("bad_request", errors.toString(), 400);
        return ResponseEntity.status(apiException.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiException.toJson());
    }
}
