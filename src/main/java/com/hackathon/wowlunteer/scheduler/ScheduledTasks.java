package com.hackathon.wowlunteer.scheduler;

import com.hackathon.wowlunteer.user.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    public ScheduledTasks(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @Scheduled(fixedRate = 60000 * 60 * 24)
    public void reportCurrentTime() {
        confirmationTokenService.deleteAllExpired();
    }
}
