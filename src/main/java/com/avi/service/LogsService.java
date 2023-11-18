package com.avi.service;

import com.avi.dto.FilterDto;
import com.avi.dto.LogDto;
import com.avi.exception.ApiException;
import com.avi.pojo.Log;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;


public interface LogsService {

    void produceLog(LogDto logDto) throws ApiException;

    void consumeAndSaveLogs(List<Log> log) throws ApiException, IOException;

    //    LogDto saveLog(LogDto request);
    List<LogDto> saveLogs(List<LogDto> request);
    List<LogDto> seacrhLogs(String searchString, FilterDto filter) throws ApiException;
    List<LogDto> getLogsInRange(ZonedDateTime startTime, ZonedDateTime endTime) throws ApiException;

}
