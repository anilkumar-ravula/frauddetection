package com.payments.notification.controller;

import com.payments.notification.model.NotificationRequest;
import com.payments.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> notify(@RequestBody NotificationRequest request) {
        logger.info("notify");
        notificationService.process(request);
        return ResponseEntity.ok("Notification processed");
    }
    @GetMapping
    public ResponseEntity<String> get() {
        logger.info("Get request");
        return ResponseEntity.ok("Welcome to notifications");
    }
}
