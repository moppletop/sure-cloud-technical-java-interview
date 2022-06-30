package com.surecloud.javatechnicalinterview.api;

import com.surecloud.javatechnicalinterview.model.AddExamResultDto;
import com.surecloud.javatechnicalinterview.model.ExamResult;
import com.surecloud.javatechnicalinterview.service.ExamResultsService;
import com.surecloud.javatechnicalinterview.service.model.AddExamResultCommand;
import com.surecloud.javatechnicalinterview.service.model.GetExamResultQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamCommandApiDelegateImpl implements ExamCommandApiDelegate {

    private final ExamResultsService examResultsService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<ExamResult> addResult(AddExamResultDto request) {
        AddExamResultCommand command = new AddExamResultCommand(request.getId(), request.getName(), request.getScore(), request.getDateTaken());

        GetExamResultQueryResult result = examResultsService.addResult(command);

        return ResponseEntity.ok(conversionService.convert(result, ExamResult.class));
    }

}
