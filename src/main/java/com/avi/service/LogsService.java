package com.avi.service;

import com.avi.dto.LogDto;
import com.avi.exception.ApiException;

import java.time.ZonedDateTime;
import java.util.List;

public interface LogsService {

    LogDto saveLog(LogDto request);
    List<LogDto> saveLogs(List<LogDto> request);
    List<LogDto> seacrhLogs(String searchString, LogDto filter) throws ApiException;
    List<LogDto> getLogsInRange(ZonedDateTime startTime, ZonedDateTime endTime) throws ApiException;
}
