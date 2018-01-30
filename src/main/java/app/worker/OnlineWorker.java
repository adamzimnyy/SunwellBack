package app.worker;

import app.model.Online;
import app.repository.OnlineRepository;
import app.util.OnlineParser;
import app.util.RetrofitBuilder;
import app.util.WakeUpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;

/**
 * Created by adamz on 29.01.2018.
 */
@Component
public class OnlineWorker {

    @Autowired
    OnlineRepository onlineRepository;


    @Scheduled(cron = "0 0/30 * * * *")
    void getOnline() {
        Online on = OnlineParser.parse();
        System.out.println("Saving online stats: Feronis = " + on.getFeronis() + ", Angrathar = " + on.getAngrathar());
        onlineRepository.save(on);

        Call<Void> wakeUpCall = ((WakeUpApi) RetrofitBuilder.getService(WakeUpApi.class, "http://sunwell-back.herokuapp.com")).wakeUp();

        try {
            wakeUpCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
