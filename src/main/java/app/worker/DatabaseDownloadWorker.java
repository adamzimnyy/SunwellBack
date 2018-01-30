package app.worker;

import app.model.Item;
import app.repository.CharacterRepository;
import app.repository.ItemRepository;
import app.util.ItemParser;
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
@ConditionalOnProperty(value = "scheduler.download.enable", havingValue = "true")
public class DatabaseDownloadWorker {

    List<Integer> itemIds = new ArrayList<>();
    int i = 0;


    @Autowired
    ItemRepository itemRepository;

    @PostConstruct
    @Async
    public void getItemIdsFromFile() {

        Resource resource = new ClassPathResource("wotlk-item-ids.txt");
        try {
            InputStream resourceInputStream = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(resourceInputStream));

            String line;
            while ((line = br.readLine()) != null) {
                int id = Integer.parseInt(line);
                itemIds.add(id);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 500)
    void getItem() {
        if(i< itemIds.size()) {
            int id = itemIds.get(i);
            while (itemRepository.findById(id) != null) {
                i++;
                id = itemIds.get(i);
            }
            try {
                Item item = ItemParser.parse(id);
                itemRepository.save(item);
            } catch (Exception e) {
                System.out.println("Item " + id + " failed to parse.");
                i++;
            }
        } else{
            System.out.println("Database update completed.");
        }
    }

}
