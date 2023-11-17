package com.avi.controller;

import com.avi.dto.LogDto;
import com.avi.exception.ApiException;
import com.avi.service.LogsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LogController {

    LogsService logsService;

    @PostMapping("log")
    public LogDto saveLog(@RequestBody LogDto logDto) {
        return logsService.saveLog(logDto);
    }

    @PostMapping("logs")
    public List<LogDto> saveLogs(@RequestBody List<LogDto> logDtos) {
        return logsService.saveLogs(logDtos);
    }

    @PostMapping("logs-search")
    public List<LogDto> getLogsFilter(@RequestParam(value = "searchString") String searchString, @RequestBody LogDto filter)
            throws ApiException {
        return logsService.seacrhLogs(searchString, filter);
    }

    @GetMapping("logs")
    public List<LogDto> getLogsInRange(@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                           ZonedDateTime startTime,
                                       @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       ZonedDateTime endTime) throws ApiException {
        return logsService.getLogsInRange(startTime, endTime);
    }
}

