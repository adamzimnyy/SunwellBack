package app.util;

import app.model.Item;
import app.repository.CharacterRepository;
import app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adamz on 29.01.2018.
 */
@Component
public class DatabaseCleanupWorker {
    private static final int HOURS = 6;

    @Autowired
    CharacterRepository characterRepository;
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void cleanupDatabase() {
        System.out.println("Cleanup worker started...");
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime ago = now.plusHours(-HOURS);
        int removedRows = characterRepository.deleteByLastUpdatedBefore(Date.from(ago.toInstant()));
        System.out.println("Cleanup removed " + removedRows + " rows.");
    }
}
