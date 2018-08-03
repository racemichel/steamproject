import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception{

        String URL = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=10AA5657A74599F1C6F849A6A277CFF7&steamid=76561198039996450&format=json";

        // Haalt data op van de STEAM API & Parsed de data.
        String output = readUrl(URL);
        JsonElement data = new JsonParser().parse(output);
        JsonObject dataobject = data.getAsJsonObject();
        JsonObject response = dataobject.getAsJsonObject("response");

        // Ophalen en laten zien van de games count.
        int gamecount = response.get("game_count").getAsInt();
        System.out.println("Michel's library has " + gamecount + " games");

        // Het ophalen en laten zien van de top 3 meest gespeelde games
        JsonArray games = response.getAsJsonArray("games");

        int highestplaytime = 0;
        int appid = 0;

        for(JsonElement game : games){
            // In het komende stuk code ga ik de hoogste playtime onthouden door steeds te vergelijken met de vorige hoogste.
            JsonObject gameObject = game.getAsJsonObject();
            int playtime = gameObject.get("playtime_forever").getAsInt();
            int spel = gameObject.get("appid").getAsInt();
            if (playtime > highestplaytime) {
                highestplaytime = playtime;
                appid = spel;

            }
        }
        System.out.println("Het spel dat ik heb meeste gespeeld heb heeft een waarde van: " + highestplaytime + " minuten");
        System.out.println("Dit spel heeft ID" + appid );
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}

