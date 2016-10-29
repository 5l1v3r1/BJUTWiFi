package me.liuyun.bjutlgn.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import cyanogenmod.app.CMStatusBarManager;
import cyanogenmod.app.CustomTile;
import me.liuyun.bjutlgn.R;
import me.liuyun.bjutlgn.db.FlowDao;
import me.liuyun.bjutlgn.tile.CMTileReceiver;
import me.liuyun.bjutlgn.widget.GraphCard;
import me.liuyun.bjutlgn.widget.StatusCard;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.status_card) CardView statusCardView;
    @BindView(R.id.graph_card) CardView graphCardView;
    public StatusCard statusCard;
    public GraphCard graphCard;
    public FlowDao dao;
    public Resources resources;
    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        dao = new FlowDao(this);
        resources = getResources();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        statusCard = new StatusCard(statusCardView, this);
        graphCard = new GraphCard(graphCardView, this);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(CMTileReceiver.ACTION_TOGGLE_STATE);
            intent.putExtra(CMTileReceiver.STATE, CMTileReceiver.STATE_OFF);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            CustomTile customTile = new CustomTile.Builder(MainActivity.this)
                    .setOnClickIntent(pendingIntent)
                    .setContentDescription("BJUT WiFi")
                    .setLabel("BJUT WiFi Off")
                    .shouldCollapsePanel(false)
                    .setIcon(R.drawable.ic_cloud_off)
                    .build();
            CMStatusBarManager.getInstance(MainActivity.this)
                    .publishTile(CMTileReceiver.CUSTOM_TILE_ID, customTile);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        statusCard.onRefresh();
        graphCard.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }
        else if(id==R.id.action_users){
            Intent intent = new Intent(this, UserActivity.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
