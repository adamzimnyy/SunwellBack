package app.controller;

import app.model.Character;
import app.model.Item;
import app.repository.CharacterRepository;
import app.repository.ItemRepository;
import app.util.CharacterParser;
import app.util.Gearscore;
import app.util.ItemParser;
import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class CharacterController {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ItemRepository itemRepository;

    @CrossOrigin
    @RequestMapping(value = {"/character/{realm}/{name}", "character/{realm}/{name}"}, method = RequestMethod.GET)
    public @ResponseBody
    Character getCharacter(@PathVariable(value = "name") String name, @PathVariable(value = "realm") String realm) {
        System.out.println("getCharacter -> " + name);
        Character c = characterRepository.findByName(name);
        List<Item> items = new ArrayList<>();
        try {
            int sum = 0, total = 0;
            if (c == null || c.getGearscore() == 0) {
                System.out.println(name + " not found or not up to date. Parsing...");
                c = CharacterParser.parse(name, realm);
                if (c == null) return null;
            }
            c.setName(name);
            System.out.println(name + " found: " + c.getInfo());
            for (Integer id : c.getItemIds()) {
                Item item;
                if ((item = itemRepository.findById(id)) != null) {
                    items.add(item);
                } else {
                    item = ItemParser.parse(id);
                    if (item != null) {
                        if(item.getItemLevel() > 180)
                        itemRepository.save(item);
                        items.add(item);
                    }
                    items.add(item);
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
        } catch (IOException e) {
            if (e instanceof HttpStatusException) {
                System.out.println("Character " + name + " doesn't exist.");
                return null;
            }
        }

        return c;
    }
}
