package com.surecloud.javatechnicalinterview.service;

import com.surecloud.javatechnicalinterview.service.model.AddExamResultCommand;
import com.surecloud.javatechnicalinterview.service.model.GetExamResultQueryResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamResultsService {

    GetExamResultQueryResult addResult(AddExamResultCommand command);

    Optional<GetExamResultQueryResult> getExam(UUID id);

    List<GetExamResultQueryResult> getExams();

}
