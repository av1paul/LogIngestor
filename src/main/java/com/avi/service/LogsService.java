package com.avi.service;

import com.avi.dto.FilterDto;
import com.avi.dto.LogDto;
import com.avi.exception.ApiException;
import com.avi.pojo.Log;

import java.io.IOException;
import java.util.List;


public interface LogsService {

    void produceLog(LogDto logDto) throws ApiException;
    void consumeAndSaveLogs(List<Log> log) throws ApiException, IOException;
    List<LogDto> seacrhLogs(String searchString, FilterDto filter) throws ApiException;
}
