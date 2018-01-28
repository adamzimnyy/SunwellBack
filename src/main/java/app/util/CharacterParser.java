package app.util;

import app.model.Character;
import app.model.Item;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamz on 26.01.2018.
 */
public class CharacterParser {

    public static final String BASE_URL = "https://sunwell.pl/armory";

    public static final String REALM = "Feronis";


    public static Character parse(String characterName) throws IOException {
        Character character = new Character();
        ArrayList<Item> items = new ArrayList<>();
        Document doc = Jsoup.connect(BASE_URL + "/" + REALM + "/" + characterName).get();
        Element race = doc.select("span[class*=character-info]").first();
        System.out.println(race);
        List<Integer> ids = new ArrayList<>();
        Elements links = doc.select("a[href*=db.darkwizard.pl?item=]");
        for (Element e : links) {
            int id = Integer.parseInt(e.attr("href").split("=")[1]);
            ids.add(id);
        }
        String info = race.text();
        character.setInfo(info);
        System.out.println(character);
        character.setItems(items);
        character.setItemIds(ids);
        return character;
    }
}
