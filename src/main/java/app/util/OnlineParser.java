package app.util;

import app.model.Online;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

/**
 * Created by adamz on 30.01.2018.
 */
public class OnlineParser {

    public static Online parse() {
        Online online = new Online();
        try {
            Document doc = Jsoup.connect("http://sunwell.pl").get();
            Elements el = doc.select("div[class=status__bar__info] > p");
            String fer = el.get(0).text().replaceAll("[A-Za-z]", "");
            String ang = el.get(1).text().replaceAll("[A-Za-z]", "");
            online.setFeronis(Integer.parseInt(fer));
            online.setAngrathar(Integer.parseInt(ang));
            online.setDate(new Date());
        } catch (IOException e) {
            return null;
        }
        return online;
    }
}
