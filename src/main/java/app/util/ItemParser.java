package app.util;

import app.model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adamz on 26.01.2018.
 */
public class ItemParser {

    static final String BASE_URL = "http://db.darkwizard.pl?item=";

    public static Item parse(int id) throws IOException {
        Document itempage = Jsoup.connect(BASE_URL + id).get();
        Pattern p = Pattern.compile("Icon.create\\('[a-z0-9_]*'");
        Matcher m = p.matcher(itempage.toString());
        m.find();
        String iconUnprocessed = m.group();

        Element s = itempage.select("script").get(13);
        if (s.data().contains(" var _ = g_items;")) {
            Item i = new Item();
            i.setIcon(iconUnprocessed.replace("'","").replace("Icon.create(",""));
            Element item = Jsoup.parseBodyFragment(s.data().replace("\\/", "/")).getElementsByTag("table").first();
            Element itemclass = item.select("b[class]").first();
            i.setQuality(Integer.parseInt(itemclass.attr("class").replaceAll("\\D+", "")));
            i.setName(itemclass.text());
            i.setSlot(item.select("tbody > tr > td").get(1).text());
            i.setHeroic(item.toString().contains("Heroic"));
            Pattern p2 = Pattern.compile("Item Level \\d{1,3}");
            Matcher m2 = p2.matcher(item.toString());
            m2.find();
            i.setId(id);
            i.setItemLevel(Integer.parseInt(m2.group().replace("Item Level ", "")));
            return i;
        }
        return null;
    }
}
