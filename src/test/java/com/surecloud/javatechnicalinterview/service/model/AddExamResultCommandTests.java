package com.surecloud.javatechnicalinterview.service.model;

import com.surecloud.javatechnicalinterview.service.exception.ExamResultCommandValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AddExamResultCommandTests {

    @Test
    void testNameValidation() {
        String message = "Name must be between 1 and 255 characters";

        assertException(new AddExamResultCommand(null, null, 1, LocalDate.EPOCH), message);
        assertException(new AddExamResultCommand(null, "", 1, LocalDate.EPOCH), message);
        assertException(new AddExamResultCommand(null, "a".repeat(256), 1, LocalDate.EPOCH), message);
    }

    @Test
    void testScoreValidation() {
        String message = "Score must be a positive integer";

        assertException(new AddExamResultCommand(null, "Name", null, LocalDate.EPOCH), message);
        assertException(new AddExamResultCommand(null, "Name", -1, LocalDate.EPOCH), message);
        assertException(new AddExamResultCommand(null, "Name", Integer.MIN_VALUE, LocalDate.EPOCH), message);
    }

    @Test
    void testDateTakenValidation() {
        String message = "Date taken must be today or a date in the past";

        assertException(new AddExamResultCommand(null, "Name", 1, null), message);
        assertException(new AddExamResultCommand(null, "Name", 1, LocalDate.MAX), message);
    }

    private void assertException(AddExamResultCommand command, String message) {
        assertThatThrownBy(command::validate)
                .isInstanceOf(ExamResultCommandValidationException.class)
                .hasMessage(message);
    }

}
