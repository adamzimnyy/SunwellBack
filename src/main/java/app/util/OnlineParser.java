package app.util;

import app.model.Online;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adamz on 30.01.2018.
 */
public class OnlineParser {

    public static Online parse() {
        Online online = new Online();
        try {


            Map<String, String> cookies = new HashMap<>();

            cookies.put("_ICC_Open", "1; expires=Fri, 08 Feb 2019 10:24:04 GMT1");
            cookies.put("__cfduid", "dfd16562c89b1f65f7ee65580f61c6d411517475900; expires=Fri, 08 Feb 2019 10:24:04 GMT1");
            cookies.put("XSRF-TOKEN", "eyJpdiI6Ijk5ak95VTRpUTlBQ1RCNnVqVktWamc9PSIsInZhbHVlIjoiNU9OWmNPY3Q2UHRtSnY3YWFQQno3T3dndk5kZVZZMnNuak83eW5xVUJoSmpNXC9Ka0NvZFN0OTlaOFdUamV3bmpmektNRVdPU2N5eUxzb092QVJLck13PT0iLCJtYWMiOiJmMDczMzhkYjYyMDc1NDA0MjBiN2I5N2FjNThmOGM3NDQwZjExMjVhODMzZjYwMTJkYzY0NGM1YTBhNmM5NjU1In0%3D; expires=Fri, 08 Feb 2019 10:24:04 GMT1");
            cookies.put("laravel_session", "eyJpdiI6InBENmlFNmdRS3JJalZVNmt3MTY5RFE9PSIsInZhbHVlIjoiWlp1ZlZlc3Vzb1FZQXplMUlGajVxNmxJN2p0Um1yWmQxVkdJZGNqZ01nWXBMSzN1Z0VBZU1jODN4S25La1JWTW1KTVM4YmFGdDB5XC94dGxIelE2TW9BPT0iLCJtYWMiOiIzM2Q3YTVmYjgyNWNkMmNjMTQwOGUzODUwYzYwYTJmZjM4MGMzNWRkYTE0NTgwYzhhODQ1YmE3N2M1NDZmNDVkIn0%3D; expires=Fri, 08 Feb 2019 10:24:04 GMT1");

            Document doc = Jsoup.connect("http://sunwell.pl").cookie("_ICC_Open","1; expires=Thu, 08 Feb 2019 10:24:04 GMT1").cookies(cookies).get();
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
