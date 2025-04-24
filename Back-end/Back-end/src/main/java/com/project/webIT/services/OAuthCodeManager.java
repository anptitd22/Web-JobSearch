package com.project.webIT.services;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class OAuthCodeManager {
    // Có thể sử dụng Redis hoặc cache phân tán trong môi trường production
    private final Set<String> usedCodes = Collections.synchronizedSet(new HashSet<>());
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public boolean isCodeUsed(String code) {
        return usedCodes.contains(code);
    }

    public void markCodeAsUsed(String code) {
        usedCodes.add(code);

        // Tùy chọn: xóa mã cũ sau một khoảng thời gian để tránh rò rỉ bộ nhớ
        scheduler.schedule(() -> {
            usedCodes.remove(code);
        }, 5, TimeUnit.MINUTES);
    }
    @PreDestroy
    public void shutdown() {
        scheduler.shutdown();
    }
}

