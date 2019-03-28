package com.hackathon.wowlunteer.user.service;

import com.hackathon.wowlunteer.user.persistence.model.ConfirmationToken;
import com.hackathon.wowlunteer.user.persistence.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import static com.hackathon.wowlunteer.scheduler.TimeConstants.ONE_DAY_IN_MILLIS;

@Service
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void deleteAllExpired() {
        for (int i = 0; i < confirmationTokenRepository.findAll().size(); i++) {
            deleteExpired(confirmationTokenRepository.findAll().get(i));
        }
    }

    private void deleteExpired(ConfirmationToken confirmationToken) {
        if (System.currentTimeMillis() - (ONE_DAY_IN_MILLIS) >= confirmationToken.getCreatedAt()
                && !confirmationToken.getApplicationUser().isEnabled()) {
            confirmationTokenRepository.deleteById(confirmationToken.getId());
        }
    }
}
