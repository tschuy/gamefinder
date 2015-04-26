package com.tschuy.gamefinder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;


public class GameDetail extends ActionBarActivity {

    Game game;

    MapFragment map_fragment;
    GoogleMap map;

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        Bundle b = getIntent().getExtras();
        game = b.getParcelable("com.tschuy.gamefinder.Game");

        map_fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = map_fragment.getMap();

        map.addMarker(new MarkerOptions().position(game.getLocationAsLatLng()).title(game.getGameType())).getId();
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(game.getLocationAsLatLng());
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);

        map.moveCamera(center);
        map.animateCamera(zoom);


        ((TextView) findViewById(R.id.user)).setText(game.getUser());
        ((TextView) findViewById(R.id.game_type)).setText(game.getGameType());
        ((TextView) findViewById(R.id.where)).setText(game.getLocationName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_detail, menu);
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
}
