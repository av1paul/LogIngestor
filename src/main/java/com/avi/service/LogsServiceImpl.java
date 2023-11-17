package com.avi.service;

import com.avi.dto.LogDto;
import com.avi.pojo.Log;
import com.avi.mapper.LogMapper;
import com.avi.repository.LogRepository;
import com.avi.util.TimeStampConverter;
import com.mongodb.WriteConcern;
import com.mongodb.bulk.BulkWriteResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {

    LogRepository logRepository;
    LogMapper logMapper;
    MongoTemplate mongoTemplate;
    TimeStampConverter timeStampConverter;

    @Override
    public LogDto saveLog(LogDto dto) {
        Log log = logMapper.getLog(dto);
        logRepository.save(log);
        return logMapper.getLogDto(log);
    }

    @Override
    public List<LogDto> saveLogs(List<LogDto> request) {
        List<Log> logs = logMapper.getLogs(request);

        mongoTemplate.setWriteConcern(WriteConcern.W1.withJournal(true));
        BulkOperations bulkInsertion = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Log.class);
        logs.forEach(bulkInsertion::insert);
        BulkWriteResult bulkWriteResult = bulkInsertion.execute();

        return logMapper.getLogDtos(logs);

    }

    @Override
    @Transactional(readOnly = true)
    public List<LogDto> seacrhLogs(String searchString, LogDto filter) {
        Map<String, Object> filterMap = getFilterMap(filter);

        Criteria criteria = new Criteria();

        criteria.orOperator(
                Criteria.where("level").regex(searchString, "i"),
                Criteria.where("message").regex(searchString, "i"),
                Criteria.where("resourceId").regex(searchString, "i"),
                Criteria.where("traceId").regex(searchString, "i"),
                Criteria.where("spanId").regex(searchString, "i"),
                Criteria.where("commit").regex(searchString, "i"),
                Criteria.where("metadata.parentResourceId").regex(searchString, "i")
        );

        for(Map.Entry<String, Object> entry : filterMap.entrySet())  {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            criteria.and(fieldName).is(value);
        }

        List<Log> logs = mongoTemplate.find(new Query(criteria), Log.class);
        System.out.println(logs);
        return logMapper.getLogDtos(logs);
    }

    @Override
    public List<LogDto> getLogsInRange(ZonedDateTime startTime, ZonedDateTime endTime) {
        Criteria criteria = new Criteria();
        criteria.and("timestamp").gte(timeStampConverter.convert(startTime)).lte(timeStampConverter.convert(endTime));
        List<Log> logs = mongoTemplate.find(new Query(criteria), Log.class);
        System.out.println(logs);
        return logMapper.getLogDtos(logs);
    }

    private Map<String, Object> getFilterMap(LogDto filter) {
        Map<String, Object> filterMap = new HashMap<>();
        for (Field field : LogDto.class.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = null;
            try {
                fieldValue = field.get(filter);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if(fieldValue == null) {
                continue;
            }
            filterMap.put(fieldName, fieldValue);
            System.out.println(fieldName);
        }
        return filterMap;
    }


}
