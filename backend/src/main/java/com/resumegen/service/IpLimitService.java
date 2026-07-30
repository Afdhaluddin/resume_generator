package com.resumegen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IpLimitService {

    @Value("${resume.free-limit:2}")
    private int freeLimit;

    // IP address -> count of generated resumes
    private final Map<String, AtomicInteger> ipUsageMap = new ConcurrentHashMap<>();

    public boolean canGenerate(String ipAddress, boolean isPaid) {
        if (isPaid) {
            return true;
        }
        AtomicInteger count = ipUsageMap.get(ipAddress);
        return count == null || count.get() < freeLimit;
    }

    public boolean canGenerate(String ipAddress) {
        return canGenerate(ipAddress, false);
    }

    public int getRemaining(String ipAddress, boolean isPaid) {
        if (isPaid) {
            return Integer.MAX_VALUE;
        }
        AtomicInteger count = ipUsageMap.get(ipAddress);
        if (count == null) {
            return freeLimit;
        }
        return Math.max(0, freeLimit - count.get());
    }

    public int getRemaining(String ipAddress) {
        return getRemaining(ipAddress, false);
    }

    public void recordGeneration(String ipAddress) {
        ipUsageMap.computeIfAbsent(ipAddress, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public int getUsage(String ipAddress) {
        AtomicInteger count = ipUsageMap.get(ipAddress);
        return count == null ? 0 : count.get();
    }

    public void resetUsage(String ipAddress) {
        ipUsageMap.remove(ipAddress);
    }
}
