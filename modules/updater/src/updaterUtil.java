import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicReference;

public class updaterUtil {

    public static String getIdAuthor (String name, String author) {
        URLConnection con;
        JSONArray arr;
        JSONObject obj;
        AtomicReference<String> id = new AtomicReference<>();

        try {
            con = new URL("https://api.spiget.org/v2/search/authors/"+author+"?fields=name%2Cid").openConnection();
            arr = (JSONArray) new JSONParser().parse(new InputStreamReader(con.getInputStream()));
            obj = (JSONObject) arr.get(0);

            con = new URL("https://api.spiget.org/v2/authors/"+obj.get("id")+"/resources?sort=-downloads&fields=name%2Cid").openConnection();
            arr = (JSONArray) new JSONParser().parse(new InputStreamReader(con.getInputStream()));

            arr.forEach((Object o) -> {
                JSONObject tObj = (JSONObject) o;
                System.out.println(tObj.get("name")+" "+name+" "+author+" "+obj.get("id"));
                if (tObj.get("name").toString().toLowerCase().contains(name.toLowerCase())) {
                    id.set(String.valueOf(tObj.get("id")));
                }
            });
            return id.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "spigotId";
        }

    }

    public static String getId (String name) {
        URLConnection con;
        JSONArray arr;
        AtomicReference<String> id = new AtomicReference<>();

        try {
            con = new URL("https://api.spiget.org/v2/search/resources/"+name+"?field=name&size=10&sort=-downloads&fields=name%2Cid").openConnection();
            arr = (JSONArray) new JSONParser().parse(new InputStreamReader(con.getInputStream()));

            arr.forEach((Object o) -> {
                JSONObject tObj = (JSONObject) o;
                if (tObj.get("name").toString().contains(name)) {
                    id.set(String.valueOf(tObj.get("id")));
                }
            });
            return id.toString();
        } catch (Exception e) {
            return "spigotId";
        }

    }

}
