package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class WeatherApp {
    private static final String KEY = "dd03095ade1b443c94061415251203";

    private static final String URL_BASE = "https://api.weatherapi.com/v1/current.json?q=";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("City: ");
        String city = sc.nextLine();
        sc.close();

        try {
            String res = getData(city);
            if (res != null) {
                showWeather(res);
            } else {
                System.out.println("Error getting data.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String getData(String city) throws Exception {
        String link = URL_BASE + city + "&appid=" + KEY + "&units=metric";
        URL url = new URL(link);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        if (con.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } else {
            return null;
        }
    }

    private static void showWeather(String res) {
        JSONObject obj = new JSONObject(res);
        String name = obj.getString("name");
        JSONObject main = obj.getJSONObject("main");
        double temp = main.getDouble("temp");
        int hum = main.getInt("humidity");
        JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);
        String desc = weather.getString("description");

        System.out.println("\n" + name);
        System.out.println("Temp: " + temp + "°C");
        System.out.println("Humidity: " + hum + "%");
        System.out.println("Condition: " + desc);
    }
    // Command to run : java -cp target/WeatherApp-1.0-SNAPSHOT.jar org.example.WeatherApp
}
