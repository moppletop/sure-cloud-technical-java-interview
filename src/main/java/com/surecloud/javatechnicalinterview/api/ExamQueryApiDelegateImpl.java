package com.surecloud.javatechnicalinterview.api;

import com.surecloud.javatechnicalinterview.model.ExamResult;
import com.surecloud.javatechnicalinterview.service.ExamResultsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamQueryApiDelegateImpl implements ExamQueryApiDelegate {

    private final ExamResultsService examResultsService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<ExamResult> getResult(UUID id) {
        return examResultsService.getExam(id)
                .map(result -> ResponseEntity.ok(conversionService.convert(result, ExamResult.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Override
    public ResponseEntity<List<ExamResult>> getResults() {
        return ResponseEntity.ok(examResultsService.getExams().stream()
                .map(result -> conversionService.convert(result, ExamResult.class))
                .collect(Collectors.toList()));
    }
}
