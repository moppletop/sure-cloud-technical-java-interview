package com.surecloud.javatechnicalinterview.api.converter;

import com.surecloud.javatechnicalinterview.model.ExamResult;
import com.surecloud.javatechnicalinterview.service.model.GetExamResultQueryResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GetExamQueryResultConverter implements Converter<GetExamResultQueryResult, ExamResult> {

    @Override
    public ExamResult convert(GetExamResultQueryResult result) {
        return new ExamResult()
                .id(result.id())
                .name(result.name())
                .score(result.score())
                .dateTaken(result.dateTaken());
    }

}
