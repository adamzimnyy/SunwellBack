package app.controller;

import app.model.Character;
import app.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import app.repository.CharacterRepository;
import app.repository.ItemRepository;
import app.util.CharacterParser;
import app.util.ItemParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class CharacterController {

   // @Autowired
  //  CharacterRepository characterRepository;

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(value={"/{name}","{name}"}, method= RequestMethod.GET)
    public @ResponseBody Character getCharacter(@PathVariable(value="name") String name) {
        System.out.println("getCharacter");
        Character c=null;
        List<Item> items = new ArrayList<>();
        try {
            int sum=0,total=0;
            c = CharacterParser.parse(name);
            for(Integer id : c.getItemIds()){
                Item item;
                if((item = itemRepository.findById(id)) != null){
                    items.add(item);
                }
                else{
                    item = ItemParser.parse(id);
                    itemRepository.save(item);
                    items.add(item);
                }
                sum+= item != null ? item.getItemLevel() : 0;
                total++;
            }
            c.setItemLevel(sum/(float)total);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return c;
    }


    @RequestMapping(value="/", method= RequestMethod.GET)
    public    String  start() {
        System.out.println("Start");
        return "Hello.";
    }
}
