package com.tschuy.gamefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import android.util.Log;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tschuy.gamefinder.GamesFragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity implements GamesFragment.OnFragmentInteractionListener {

    GamesFragment gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Ion.with(getApplicationContext())
                .load("http://140.211.9.30:443/games")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result == null) {
                            Log.v("GAME", "result is null");
                            e.printStackTrace();
                        }
                        ArrayList<Game> games = new ArrayList<Game>();
                        Iterator<JsonElement> it = result.iterator();
                        while (it.hasNext()) {
                            games.add(new Game(it.next().getAsJsonObject()));
                        }
                        gameList.addGames(games);
                        Log.v("GAME", "done!");
                    }
                });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            gameList = new GamesFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, (Fragment) gameList)
                    .commit();
        }
    }

    public void onFragmentInteraction(String id) {
        Toast.makeText(getApplicationContext(), "Interaction!", Toast.LENGTH_LONG).show();
    }

    public void gameClicked(Game game) {
        Toast.makeText(getApplicationContext(), game.toString(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, GameDetail.class);
        i.putExtra("com.tschuy.gamefinder.Game", game);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_games, container, false);
            return rootView;
        }
    }
}
