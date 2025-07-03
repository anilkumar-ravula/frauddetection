package com.payments.notification.model;

import lombok.Data;

@Data
public class NotificationRequest {
    private String channel;
    private String recipient;
    private String message;
    private String incidentId;
}
