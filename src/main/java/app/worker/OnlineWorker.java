package app.worker;

import app.model.Online;
import app.repository.CharacterRepository;
import app.repository.OnlineRepository;
import app.util.OnlineParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by adamz on 30.01.2018.
 */
@Component
public class OnlineWorker {

    @Autowired
    OnlineRepository onlineRepository;

    @Scheduled(cron ="0 0/30 0 ? * * *")
    public void checkOnline() {
        Online on = OnlineParser.parse();
        onlineRepository.save(on);
    }
}
