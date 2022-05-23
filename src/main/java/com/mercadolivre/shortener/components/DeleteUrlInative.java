package com.mercadolivre.shortener.components;

import com.mercadolivre.shortener.repositories.ShortenerRepositorie;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DeleteUrlInative {

    private final ShortenerRepositorie shortenerRepositorie;

    public DeleteUrlInative(ShortenerRepositorie shortenerRepositorie) {
        this.shortenerRepositorie = shortenerRepositorie;
    }

    @Scheduled(cron = "@daily")
//    @Scheduled(fixedDelay = 5000)
    public void deleteUrls() {
        int DAYS = 90;
        shortenerRepositorie.deleteAllInativeUrl(DAYS);
    }
}
