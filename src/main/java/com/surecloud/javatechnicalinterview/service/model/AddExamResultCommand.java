package com.surecloud.javatechnicalinterview.service.model;


import com.surecloud.javatechnicalinterview.service.exception.ExamResultCommandValidationException;

import java.time.LocalDate;
import java.util.UUID;

public record AddExamResultCommand(UUID id, String name, Integer score, LocalDate dateTaken) implements ValidatableCommand {

    @Override
    public void validate() {
        if (name == null || name.length() < 1 || name.length() > 255) {
            throw new ExamResultCommandValidationException("Name must be between 1 and 255 characters");
        }

        if (score == null || score < 0) {
            throw new ExamResultCommandValidationException("Score must be a positive integer");
        }

        if (dateTaken == null || dateTaken.isAfter(LocalDate.now())) {
            throw new ExamResultCommandValidationException("Date taken must be today or a date in the past");
        }
    }

}
