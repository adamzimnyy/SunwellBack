package app.util;

import app.model.Character;
import app.model.Item;
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


    public static Character parse(String characterName,String realm) {
        long startTime = System.currentTimeMillis();

        Character character = new Character();
        ArrayList<Item> items = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(BASE_URL + "/" + realm + "/" + characterName).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Element race = doc.select("span[class*=character-info]").first();
        List<Integer> ids = new ArrayList<>();
        Elements links = doc.select("a[href*=db.darkwizard.pl?item=]");
        for (Element e : links) {
            int id = Integer.parseInt(e.attr("href").split("=")[1]);
            ids.add(id);
        }
        String info = race.text();
        character.setInfo(info);
        character.setItems(items);
        character.setItemIds(ids);
        long endTime = System.currentTimeMillis();

        System.out.println("Parsing of " + BASE_URL + "/" + realm + "/" + characterName + " took " + (endTime - startTime) + " milliseconds.");
        return character;
    }
}
