package com.avi.controller;

import com.avi.dto.FilterDto;
import com.avi.dto.LogDto;
import com.avi.exception.ApiException;
import com.avi.service.LogsService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> saveLog(@RequestBody LogDto logDto) throws ApiException {
        logsService.produceLog(logDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("logs")
    public ResponseEntity<Void> saveLogs(@RequestBody List<LogDto> logDtos) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("logs-search")
    public ResponseEntity<List<LogDto>> getLogsFilter(@RequestParam(value = "searchString") String searchString, @RequestBody @Valid FilterDto filter)
            throws ApiException {
        return new ResponseEntity<>(logsService.seacrhLogs(searchString, filter), HttpStatus.OK);
    }

    @GetMapping("logs")
    public ResponseEntity<List<LogDto>> getLogsInRange(@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                           ZonedDateTime startTime,
                                       @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       ZonedDateTime endTime) throws ApiException {
        return new ResponseEntity<>(logsService.getLogsInRange(startTime, endTime), HttpStatus.OK);
    }
}

