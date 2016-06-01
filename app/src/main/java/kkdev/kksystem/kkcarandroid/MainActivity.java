package kkdev.kksystem.kkcarandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Info) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_layout, frg_Info.newInstance())
                    .commit();
        } else if (id == R.id.nav_diag_ce) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_layout, frg_Diag.newInstance())
                    .commit();
        } else if (id == R.id.nav_diag_full) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_layout, frg_Diag.newInstance())
                    .commit();
        } else if (id == R.id.nav_media) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_layout, frg_Media.newInstance())
                    .commit();

        } else if (id == R.id.nav_infodisplay) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_layout, frg_RemoteDisplayAndr.newInstance())
                    .commit();

        } else if (id == R.id.nav_leddebug) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_layout, frg_RemoteDisplayLED.newInstance())
                    .commit();

        } else if (id == R.id.nav_settings) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_layout, frg_Settings.newInstance())
                    .commit();
        } else if (id == R.id.nav_connect_bluetooth) {
            Intent intent = new Intent(this, act_bluetoothconnect.class);
            startActivity(intent);
        } else if (id == R.id.nav_connect_dingocloud) {
            Intent intent = new Intent(this, act_dingocloud_connect.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
