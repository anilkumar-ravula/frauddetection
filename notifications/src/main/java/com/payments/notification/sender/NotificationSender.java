package com.payments.notification.sender;


import com.payments.notification.model.NotificationRequest;

public interface NotificationSender {
    void send(NotificationRequest notification);
    String getChannel(); // e.g. EMAIL, SLACK, etc.
}
