package app.worker;

import app.repository.CharacterRepository;
import app.repository.OnlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by adamz on 29.01.2018.
 */
@Component
public class DatabaseCleanupWorker {
    private static final int CHARACTER_HOURS = 6;
    private static final int ONLINE_DAYS = 7;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    OnlineRepository onlineRepository;

    @Scheduled(fixedRate = 1000 * 60 * 10)
    @Transactional
    public void cleanupCharacterDatabase() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime hoursAgo = now.plusHours(-CHARACTER_HOURS);
        int removedRows = characterRepository.deleteByLastUpdatedBefore(Date.from(hoursAgo.toInstant()));
        System.out.println("Character cleanup: removed " + removedRows + " rows.");
    }

    @Scheduled(fixedRate = 1000 * 60 * 10)
    @Transactional
    public void cleanupOnlineDatabase() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime ago = now.plusDays(-ONLINE_DAYS);
        int removedRows = onlineRepository.deleteByDateBefore(Date.from(ago.toInstant()));
        System.out.println("Online cleanup: removed " + removedRows + " rows.");
    }
}
