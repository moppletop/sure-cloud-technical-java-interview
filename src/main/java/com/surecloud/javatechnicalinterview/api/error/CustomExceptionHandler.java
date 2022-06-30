package com.surecloud.javatechnicalinterview.api.error;

import com.surecloud.javatechnicalinterview.model.Error;
import com.surecloud.javatechnicalinterview.service.exception.DuplicateExamResultException;
import com.surecloud.javatechnicalinterview.service.exception.ExamResultCommandValidationException;
import com.surecloud.javatechnicalinterview.service.exception.ExamResultsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    private final Map<Class<? extends ExamResultsException>, HttpStatus> exceptionStatuses = new HashMap<>();

    public CustomExceptionHandler() {
        exceptionStatuses.put(DuplicateExamResultException.class, HttpStatus.BAD_REQUEST);
        exceptionStatuses.put(ExamResultCommandValidationException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
        HttpStatus status = exceptionStatuses.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .status(status)
                .body(new Error()
                        .detail(ex.getMessage())
                );
    }

}
