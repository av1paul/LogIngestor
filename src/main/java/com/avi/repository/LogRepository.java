package com.avi.repository;


import com.avi.pojo.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public interface LogRepository extends MongoRepository<Log, String> {

}