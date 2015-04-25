package com.tschuy.gamefinder;

import android.location.Location;
import android.media.Image;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Game {


    public int getId() {
        return this.id;
    }

    public String getUser() {
        return this.user;
    }

    public Date getTime() {
        return this.time;
    }

    public String getGameType() {
        return this.game_type;
    }

    public String getLocationName() {
        return this.location_name;
    }

    public Location getLocation() {
        return this.location;
    }

    public int getLocationId() {
        return this.location_id;
    }

    private int id;
    private String user;
    private Date time;
    private String game_type;
    private String location_name;
    private Location location;
    private int location_id;

    private Date stringToDate(String inputdate) {
        String format = "yyyy-MM-dd'T'HH:mm:ss";
        inputdate = inputdate.substring(0, inputdate.length() - 2); // trim the ending Z

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(inputdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getImage() {
        return null;
    }

    public String toString() {
        return this.user + " is playing at " + time.toString();
    }

    public Game(JsonObject GameJson) {
        this.id = GameJson.get("id").getAsInt();
        this.user = GameJson.get("user").getAsString();
        this.time = this.stringToDate(GameJson.get("time").getAsString());
        this.game_type = GameJson.get("game_type").getAsString();

        JsonObject location_json = GameJson.get("location").getAsJsonObject();
        this.location_id = location_json.get("id").getAsInt();
        this.location_name = location_json.get("name").getAsString();

        this.location = new Location("");
        this.location.setLatitude(location_json.get("lat").getAsDouble());
        this.location.setLongitude(location_json.get("long").getAsDouble());
    }

}
