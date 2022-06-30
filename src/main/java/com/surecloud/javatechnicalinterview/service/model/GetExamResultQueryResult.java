package com.surecloud.javatechnicalinterview.service.model;

import java.time.LocalDate;
import java.util.UUID;

public record GetExamResultQueryResult(UUID id, String name, int score, LocalDate dateTaken) {

}