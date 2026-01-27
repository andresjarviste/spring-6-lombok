package guru.springframework.restmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException ex) {
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if (ex.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) ex.getCause().getCause();
            List<Map<String, String>> errors = exception.getConstraintViolations().stream()
                .map(constraintViolation -> {
                    //return constraintViolation.getPropertyPath().toString() + " : " + constraintViolation.getMessage();
                    Map<String, String> error = new HashMap<>();
                    error.put(
                        constraintViolation.getPropertyPath().toString(), 
                        constraintViolation.getMessage()
                    );
                    return error;
                }).collect(Collectors.toList());
            return responseEntity.body(errors);
        }

        return responseEntity.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException ex) {
        List errorList = ex.getFieldErrors().stream()
        .map(fieldError -> {
            Map<String, String> error = new HashMap<>();
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
            return error;

        }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorList);
    }
    
}
