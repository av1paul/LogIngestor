package com.avi.service;

import com.avi.dto.LogDto;

import java.time.ZonedDateTime;
import java.util.List;

public interface LogsService {

    LogDto saveLog(LogDto request);
    List<LogDto> saveLogs(List<LogDto> request);
    List<LogDto> seacrhLogs(String searchString, LogDto filter);
    List<LogDto> getLogsInRange(ZonedDateTime startTime, ZonedDateTime endTime);
}
