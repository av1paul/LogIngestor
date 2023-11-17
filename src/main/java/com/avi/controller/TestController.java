package com.avi.controller;

import com.avi.pojo.Log;
import com.avi.dto.LogDto;
import com.avi.dto.MetaDataDto;
import com.avi.dto.TimePeriodDto;
import com.avi.mapper.LogMapper;
import com.avi.repository.LogRepository;
import com.avi.util.TimeStampConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("123")
public class TestController {

    @Autowired
    LogRepository logRepository;
    @Autowired
    LogMapper logMapper;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    TimeStampConverter timeStampConverter;


    @ApiIgnore
    @GetMapping("/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("logs")
    public List<Log> getLogs() {
        return logRepository.findAll();
    }

    @PostMapping("log")
    public LogDto save(@RequestBody LogDto request) {
        Log log = logMapper.getLog(request);
        logRepository.save(log);
        return logMapper.getLogDto(log);
    }

    @PostMapping("logs")
    public List<LogDto> save(@RequestBody List<LogDto> request) {
        List<Log> logs = logMapper.getLogs(request);
        logRepository.saveAll(logs);
        return logMapper.getLogDtos(logs);
    }

    @PostMapping("filter")
    public List<LogDto> filter(@RequestBody LogDto request) {
        //Log log = logMapper.forward(request);
        Map<String, Object> map = convertObjectToMap(request);
        Criteria criteria = new Criteria();
        for(Map.Entry<String, Object> entry : map.entrySet())  {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            if(value == null) {
                continue;
            }
            if(fieldName.equals("metadata")) {
                MetaDataDto metaData = (MetaDataDto) value;
                if(metaData.getParentResourceId() != null) {
                    criteria.and("metadata.parentResourceId").is(metaData.getParentResourceId());
                }

            } else {
                criteria.and(fieldName).is(value);
            }

        }
        List<Log> logs = mongoTemplate.find(new Query(criteria), Log.class);
        System.out.println(logs);
        return logMapper.getLogDtos(logs);
    }

    @PostMapping("range")
    public void filter(@RequestBody TimePeriodDto period) {
        Criteria criteria = new Criteria();
        criteria.and("timestamp").gte(timeStampConverter.convert(period.getStart())).lte(timeStampConverter.convert(period.getEnd()));
        List<Log> logs = mongoTemplate.find(new Query(criteria), Log.class);
        System.out.println(Log.class);
    }

    public static Map<String, Object> convertObjectToMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                Object fieldValue = field.get(object);
                map.put(fieldName, fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Handle or log the exception as needed
            }
            System.out.println(fieldName);
        }
        return map;
    }
}
