package com.avi.service;

import com.avi.dto.LogDto;
import com.avi.dto.MetaDataDto;
import com.avi.exception.ApiException;
import com.avi.exception.ErrorCode;
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
import java.util.ArrayList;
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
        logRepository.saveAll(logs);
        return logMapper.getLogDtos(logs);

    }

    @Override
    @Transactional(readOnly = true)
    public List<LogDto> seacrhLogs(String searchString, LogDto filter) throws ApiException {
        Map<String, Object> filterMap = getFilterMap(filter);
        Criteria criteria = new Criteria();
        applyRegex(searchString, criteria);
        applyFilter(filterMap, criteria);
        List<Log> logs = mongoTemplate.find(new Query(criteria), Log.class);
        System.out.println(logs);
        return logMapper.getLogDtos(logs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogDto> getLogsInRange(ZonedDateTime startTime, ZonedDateTime endTime) throws ApiException {
        if(startTime.isAfter(endTime)) {
            throw new ApiException("Start Time can't be after End Time", ErrorCode.BAD_REQUEST);
        }
        Criteria criteria = new Criteria();
        criteria.and("timestamp").gte(timeStampConverter.convert(startTime)).lte(timeStampConverter.convert(endTime));
        List<Log> logs = mongoTemplate.find(new Query(criteria), Log.class);
        System.out.println(logs);
        return logMapper.getLogDtos(logs);
    }

    private Map<String, Object> getFilterMap(LogDto filter) throws ApiException {
        Map<String, Object> filterMap = new HashMap<>();
        for (Field field : LogDto.class.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = null;
            try {
                fieldValue = field.get(filter);
            } catch (IllegalAccessException e) {
                throw new ApiException("Illegal Access", ErrorCode.INTERNAL_SERVER_ERROR);
            }
            if(fieldValue == null) {
                continue;
            }
            filterMap.put(fieldName, fieldValue);
            System.out.println(fieldName);
        }
        return filterMap;
    }

    private void applyRegex(String searchString, Criteria criteria) {
        List<Criteria> fieldCriteria = new ArrayList<>();
        for(Field field : Log.class.getDeclaredFields()) {
            String fieldName = field.getName();
            if(fieldName.equals("metadata")) {
                fieldCriteria.add(Criteria.where("metadata.parentResourceId").regex(searchString, "i"));
                continue;
            }
            if(fieldName.equals("timestamp")) {
                continue;
            }
            fieldCriteria.add(Criteria.where(fieldName).regex(searchString, "i"));
        }
        criteria.orOperator(fieldCriteria);
    }

    private void applyFilter(Map<String, Object> filterMap, Criteria criteria) {
        for(Map.Entry<String, Object> entry : filterMap.entrySet())  {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            if(fieldName.equals("metadata")) {
                MetaDataDto metaData = (MetaDataDto) value;
                if (metaData.getParentResourceId() != null) {
                    criteria.and("metadata.parentResourceId").is(metaData.getParentResourceId());
                }
                continue;
            }
            criteria.and(fieldName).is(value);
        }
    }


}
