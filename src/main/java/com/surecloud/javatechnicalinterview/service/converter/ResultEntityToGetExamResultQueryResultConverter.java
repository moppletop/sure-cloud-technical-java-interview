package com.surecloud.javatechnicalinterview.service.converter;

import com.surecloud.javatechnicalinterview.db.entity.ResultEntity;
import com.surecloud.javatechnicalinterview.service.model.GetExamResultQueryResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ResultEntityToGetExamResultQueryResultConverter implements Converter<ResultEntity, GetExamResultQueryResult> {

    @Override
    public GetExamResultQueryResult convert(ResultEntity entity) {
        return new GetExamResultQueryResult(
                entity.getId(),
                entity.getName(),
                entity.getScore(),
                entity.getDateTaken()
        );
    }
}
