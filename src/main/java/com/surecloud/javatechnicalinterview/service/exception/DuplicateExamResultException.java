package com.surecloud.javatechnicalinterview.service.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DuplicateExamResultException extends ExamResultsException {

    private final UUID resultId;

    public DuplicateExamResultException(UUID resultId) {
        super("A result with id " + resultId + " already exists");

        this.resultId = resultId;
    }

}
