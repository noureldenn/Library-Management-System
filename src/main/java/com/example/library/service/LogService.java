package com.example.library.service;

import com.example.library.entity.Log;
import com.example.library.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void createLog(String username, String action, String entityType, Long entityId) {
        Log log = new Log();
        log.setUsername(username);
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);

        logRepository.save(log);
    }
}
