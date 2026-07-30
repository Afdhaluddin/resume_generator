package com.resumegen.service;

import com.resumegen.dto.request.ResumeRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ResumeCacheService {

    private final Map<String, ResumeRequest> resumeCache = new ConcurrentHashMap<>();

    public String saveResume(ResumeRequest request) {
        String id = UUID.randomUUID().toString();
        resumeCache.put(id, request);
        return id;
    }

    public ResumeRequest getResume(String id) {
        return resumeCache.get(id);
    }

    public ResumeRequest updateResume(String id, ResumeRequest request) {
        resumeCache.put(id, request);
        return request;
    }

    public boolean exists(String id) {
        return resumeCache.containsKey(id);
    }
}
