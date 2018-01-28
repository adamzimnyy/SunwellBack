package app.controller;

import app.model.Character;
import app.model.Item;
import app.repository.CharacterRepository;
import app.repository.ItemRepository;
import app.util.CharacterParser;
import app.util.ItemParser;
import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class CharacterController {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ItemRepository itemRepository;

    @CrossOrigin(origins = "https://sunwell.herokuapp.com")
    @RequestMapping(value = {"/{name}", "{name}"}, method = RequestMethod.GET)
    public @ResponseBody
    Character getCharacter(@PathVariable(value = "name") String name) {
        System.out.println("getCharacter -> " + name);
        Character c = characterRepository.findByName(name);
        List<Item> items = new ArrayList<>();
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime dayAgo = now.plusDays(-1);

        try {
            int sum = 0, total = 0;
            if (c == null || c.getLastUpdated().toInstant().isBefore(dayAgo.toInstant())) {
                System.out.println(name + " not found or not up to date. Parsing...");
                c = CharacterParser.parse(name);
            }
            c.setName(name);
            for (Integer id : c.getItemIds()) {
                Item item;
                if ((item = itemRepository.findById(id)) != null) {
                    items.add(item);
                } else {
                    item = ItemParser.parse(id);
                    System.out.println("Item "+item.getName()+" added.");
                    itemRepository.save(item);
                    items.add(item);
                }
                if(item.getSlot().equalsIgnoreCase("shirt") || item.getSlot().equalsIgnoreCase("tabard"))
                    continue;
                sum += item.getItemLevel();
                total++;
            }
            c.setItemLevel(sum / (float) total);
            c.setItems(items);
            c.setLastUpdated(new Date());
            characterRepository.save(c);
        } catch (IOException e) {
            if (e instanceof HttpStatusException) {
                System.out.println("Character "+name+" doesn't exist.");
                return null;
            }
        }

        return c;
    }

    @RequestMapping(value = {"get/Rdudu"}, method = RequestMethod.GET)
    public @ResponseBody
    String getRdudu() {
        System.out.println("Rdudu downloaded.");
        return "{\n" +
                "    \"name\": \"Rdudu\",\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"id\": 48211,\n" +
                "            \"name\": \"Malfurion's Headguard of Triumph\",\n" +
                "            \"itemLevel\": 245,\n" +
                "            \"heroic\": false,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Head\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47915,\n" +
                "            \"name\": \"Collar of Ceaseless Torment\",\n" +
                "            \"itemLevel\": 245,\n" +
                "            \"heroic\": true,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Neck\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 48208,\n" +
                "            \"name\": \"Malfurion's Shoulderpads of Triumph\",\n" +
                "            \"itemLevel\": 245,\n" +
                "            \"heroic\": false,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Shoulder\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 45224,\n" +
                "            \"name\": \"Drape of the Lithe\",\n" +
                "            \"itemLevel\": 226,\n" +
                "            \"heroic\": false,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Back\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47004,\n" +
                "            \"name\": \"Cuirass of Calamitous Fate\",\n" +
                "            \"itemLevel\": 258,\n" +
                "            \"heroic\": true,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Chest\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47581,\n" +
                "            \"name\": \"Bracers of Swift Death\",\n" +
                "            \"itemLevel\": 245,\n" +
                "            \"heroic\": false,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Wrist\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47945,\n" +
                "            \"name\": \"Gloves of the Silver Assassin\",\n" +
                "            \"itemLevel\": 245,\n" +
                "            \"heroic\": true,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Hands\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47107,\n" +
                "            \"name\": \"Belt of the Merciless Killer\",\n" +
                "            \"itemLevel\": 245,\n" +
                "            \"heroic\": false,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Waist\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 46975,\n" +
                "            \"name\": \"Leggings of the Broken Beast\",\n" +
                "            \"itemLevel\": 258,\n" +
                "            \"heroic\": true,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Legs\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 45564,\n" +
                "            \"name\": \"Footpads of Silence\",\n" +
                "            \"itemLevel\": 226,\n" +
                "            \"heroic\": false,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Feet\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47934,\n" +
                "            \"name\": \"Planestalker Signet\",\n" +
                "            \"itemLevel\": 245,\n" +
                "            \"heroic\": true,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Finger\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47075,\n" +
                "            \"name\": \"Ring of Callous Aggression\",\n" +
                "            \"itemLevel\": 258,\n" +
                "            \"heroic\": true,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Finger\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 50198,\n" +
                "            \"name\": \"Needle-Encrusted Scorpion\",\n" +
                "            \"itemLevel\": 232,\n" +
                "            \"heroic\": false,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Trinket\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47131,\n" +
                "            \"name\": \"Death's Verdict\",\n" +
                "            \"itemLevel\": 258,\n" +
                "            \"heroic\": true,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Trinket\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47130,\n" +
                "            \"name\": \"Lupine Longstaff\",\n" +
                "            \"itemLevel\": 258,\n" +
                "            \"heroic\": true,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Two-Hand\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 47668,\n" +
                "            \"name\": \"Idol of Mutilation\",\n" +
                "            \"itemLevel\": 245,\n" +
                "            \"heroic\": false,\n" +
                "            \"quality\": 4,\n" +
                "            \"slot\": \"Relic\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"itemIds\": [\n" +
                "        48211,\n" +
                "        47915,\n" +
                "        48208,\n" +
                "        45224,\n" +
                "        47004,\n" +
                "        47581,\n" +
                "        47945,\n" +
                "        47107,\n" +
                "        46975,\n" +
                "        45564,\n" +
                "        47934,\n" +
                "        47075,\n" +
                "        50198,\n" +
                "        47131,\n" +
                "        47130,\n" +
                "        47668\n" +
                "    ],\n" +
                "    \"itemLevel\": 245.875,\n" +
                "    \"info\": \"80 Night Elf Druid\"\n" +
                "}";
    }
}
