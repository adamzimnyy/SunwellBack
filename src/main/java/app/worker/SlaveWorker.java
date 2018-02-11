package app.worker;

import app.model.Character;
import app.model.Item;
import app.model.Slave;
import app.repository.CharacterRepository;
import app.repository.ItemRepository;
import app.repository.SlaveRepository;
import app.util.CharacterParser;
import app.util.Gearscore;
import app.util.ItemParser;
import app.util.SlaveMarketParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by adamz on 29.01.2018.
 */
@Component
public class SlaveWorker {

    @Autowired
    SlaveRepository slaveRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ItemRepository itemRepository;

     @Scheduled(cron = "0 0/20 * * * *")
   // @Scheduled(fixedRate = 1000000)
    @Transactional
    void getSlaves() {

        List<Slave> all = slaveRepository.findAll();
        List<Slave> remove = new ArrayList<>();

        try {
            Map<String, Integer> slaveNames = SlaveMarketParser.getSlaveNames();
            List<Slave> slaves = new ArrayList<>();
            for (String s : slaveNames.keySet()) {
                Slave slave = new Slave();
                slave.setName(s);
                slave.setPrice(slaveNames.get(s));
                slaves.add(slave);
            }

            for (Slave s : all) {
                if (slaveNames.get(s.getName()) == null) {
                    remove.add(s);
                }
            }

            slaveRepository.delete(remove);

            for (Slave s : slaves) {
                if (slaveRepository.findOne(s.getName()) == null) {
                    System.out.println("Slave " + s.getName() + " not in database.");
                    Character c = characterRepository.findByName(s.getName());

                    List<Item> items = new ArrayList<>();
                    try {
                        int sum = 0, total = 0;
                        if (c == null || c.getGearscore() == 0) {
                            c = CharacterParser.parse(s.getName(), "Feronis");
                            if (c == null) continue;
                        }
                        c.setName(s.getName());
                        s.setLevel(Integer.parseInt(c.getInfo().replaceAll("[A-Za-z ]", "")));

                        for (Integer id : c.getItemIds()) {
                            Item item;
                            if ((item = itemRepository.findById(id)) != null) {
                                items.add(item);
                            } else {
                                item = ItemParser.parse(id);
                                if (item != null) {
                                    if (item.getItemLevel() > 180)
                                        itemRepository.save(item);
                                    items.add(item);
                                }
                            }
                            if (item.getSlot().equals("Tabard") || item.getSlot().equals("Shirt")) {
                                continue;
                            }
                            sum += item.getItemLevel();
                            total++;
                        }
                        c.setItemLevel(sum / (float) total);
                        c.setItems(items);

                        c.setGearscore((int) Math.round(Gearscore.getCharacterScore(c)));
                        c.setLastUpdated(new Date());
                        characterRepository.save(c);

                        s.setGearscore(c.getGearscore());
                        s.setLevel(Integer.parseInt(c.getInfo().replaceAll("[A-Za-z ]", "")));
                        String[] split = c.getInfo().split(" ");
                        String race = split[1];
                        if (race.equals("Night") || race.equals("Blood")) race += " Elf";
                        String clas = c.getInfo().split(" ")[split.length - 1];
                        if (clas.equals("Knight")) clas = "Death Knight";

                        s.setRace(race);
                        s.setClas(clas);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    slaveRepository.save(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
