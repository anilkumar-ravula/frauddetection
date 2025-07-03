package com.payments.notification.service;

import com.payments.notification.model.NotificationRequest;
import com.payments.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final List<NotificationSender> senders;

    public void process(NotificationRequest request) {


        Optional<NotificationSender> senderOpt = senders.stream()
                .filter(s -> s.getChannel().equalsIgnoreCase(request.getChannel()))
                .findFirst();

        try {
            if (senderOpt.isPresent()) {
                senderOpt.get().send(request);
            } else {
                throw new IllegalArgumentException("Unsupported channel");
            }
        } catch (Exception e) {

        }

    }
}
