package app.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adamz on 30.01.2018.
 */
public class SlaveMarketParser {

    public static Map<String, Integer> getSlaveNames() {
        System.out.println("Parse slave");
        Map<String, Integer> results = new HashMap<>();

        Map<String, String> cookies = new HashMap<>();

        cookies.put("_ICC_Open", "1; expires=Fri, 08 Feb 2019 10:24:04 GMT1");
        cookies.put("__cfduid", "dfd16562c89b1f65f7ee65580f61c6d411517475900; expires=Fri, 08 Feb 2019 10:24:04 GMT1");
        cookies.put("XSRF-TOKEN", "eyJpdiI6Ijk5ak95VTRpUTlBQ1RCNnVqVktWamc9PSIsInZhbHVlIjoiNU9OWmNPY3Q2UHRtSnY3YWFQQno3T3dndk5kZVZZMnNuak83eW5xVUJoSmpNXC9Ka0NvZFN0OTlaOFdUamV3bmpmektNRVdPU2N5eUxzb092QVJLck13PT0iLCJtYWMiOiJmMDczMzhkYjYyMDc1NDA0MjBiN2I5N2FjNThmOGM3NDQwZjExMjVhODMzZjYwMTJkYzY0NGM1YTBhNmM5NjU1In0%3D; expires=Fri, 08 Feb 2019 10:24:04 GMT1");
        cookies.put("laravel_session", "eyJpdiI6InBENmlFNmdRS3JJalZVNmt3MTY5RFE9PSIsInZhbHVlIjoiWlp1ZlZlc3Vzb1FZQXplMUlGajVxNmxJN2p0Um1yWmQxVkdJZGNqZ01nWXBMSzN1Z0VBZU1jODN4S25La1JWTW1KTVM4YmFGdDB5XC94dGxIelE2TW9BPT0iLCJtYWMiOiIzM2Q3YTVmYjgyNWNkMmNjMTQwOGUzODUwYzYwYTJmZjM4MGMzNWRkYTE0NTgwYzhhODQ1YmE3N2M1NDZmNDVkIn0%3D; expires=Fri, 08 Feb 2019 10:24:04 GMT1");

        Document doc = null;
        try {
            doc = Jsoup.connect("https://sunwell.pl/ucp/slave-market").cookies(cookies).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null) return null;
         Element table = doc.getElementById("slave_market_table");
        Elements rows = table.select("tr");
        for (int i =1;i<rows.size();i++) {
            Element row = rows.get(i);
            String name = row.selectFirst("td > a[href]").text();
            int price = Integer.parseInt(row.select("td > button").first().text());
            results.put(name,price);
        }
        System.out.println(results);
        return results;
    }
}
