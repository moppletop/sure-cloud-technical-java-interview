package com.surecloud.javatechnicalinterview.service;

import com.surecloud.javatechnicalinterview.db.entity.ResultEntity;
import com.surecloud.javatechnicalinterview.db.repository.ResultRepository;
import com.surecloud.javatechnicalinterview.service.exception.DuplicateExamResultException;
import com.surecloud.javatechnicalinterview.service.model.AddExamResultCommand;
import com.surecloud.javatechnicalinterview.service.model.GetExamResultQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatabaseExamResultsService implements ExamResultsService {

    private final ResultRepository repository;
    private final ConversionService conversionService;

    @Override
    public GetExamResultQueryResult addResult(AddExamResultCommand command) {
        Objects.requireNonNull(command, "Command cannot be null");
        command.validate();

        ResultEntity entity = new ResultEntity();
        UUID id = command.id();

        // Criteria not clear on whether the ids are supplied by the caller. If not given, generate a random one
        if (id == null) {
            id = UUID.randomUUID();
        } else {
            repository.findById(id).ifPresent(resultEntity -> {
                throw new DuplicateExamResultException(resultEntity.getId());
            });
        }

        entity.setId(id);
        entity.setName(command.name());
        entity.setScore(command.score());
        entity.setDateTaken(command.dateTaken());

        entity = repository.save(entity);

        return toResult(entity);
    }

    @Override
    public Optional<GetExamResultQueryResult> getExam(UUID id) {
        return repository.findById(id)
                .map(this::toResult);
    }

    @Override
    public List<GetExamResultQueryResult> getExams() {
        return repository.findAll().stream()
                .map(this::toResult)
                .collect(Collectors.toList());
    }

    private GetExamResultQueryResult toResult(ResultEntity entity) {
        return conversionService.convert(entity, GetExamResultQueryResult.class);
    }
}
