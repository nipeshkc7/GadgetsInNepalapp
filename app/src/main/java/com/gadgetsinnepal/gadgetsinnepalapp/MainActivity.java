package com.gadgetsinnepal.gadgetsinnepalapp;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        homeFragment fragment=new homeFragment();
//        android.support.v4.app.FragmentTransaction fragmentTransaction =
//                getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container,fragment);
//        fragmentTransaction.commit();


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.Fragment f=null;
        android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        homeFragment frag=null;
        techNews frag2=null;

        if (id == R.id.nav_camera) {
//            homeFragment fragment=new homeFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction =
//                    getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.addToBackStack("home").commit();
            frag= (homeFragment) getSupportFragmentManager().findFragmentByTag("home");

            if(frag==null){
                homeFragment fragment=new homeFragment();
              ft.add(R.id.fragment_container,fragment,"home");
            //    ft.show(fragment);
            ft.commit();
            }else{
                //ft.replace(R.id.fragment_container,frag,"home");
                ft.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
                ft.show(frag);
                ft.commit();
                Toast.makeText(getApplicationContext(),"houston",Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_gallery) {

//            techNews fragment=new techNews();
//            android.support.v4.app.FragmentTransaction fragmentTransaction =
//                    getSupportFragmentManager().beginTransaction();
//            Log.w("IDOFDESTROYED",":"+getSupportFragmentManager().findFragmentById(R.id.fragment_container).getTag());
//            fragmentTransaction.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
//            fragmentTransaction.add(R.id.fragment_container,fragment,"tech");
//            fragmentTransaction.commit();


            frag2= (techNews) getSupportFragmentManager().findFragmentByTag("tech");

            if(frag2==null){
                techNews fragment=new techNews();
                ft.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
                ft.add(R.id.fragment_container,fragment,"tech");
                ft.commit();
            }else{
                ft.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
                ft.show(frag2);
                ft.commit();
                Toast.makeText(getApplicationContext(),"GSW",Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_slideshow) {

            nepal fragment=new nepal();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment,"nepal");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_manage) {
            mobilePrice fragment=new mobilePrice();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment,"mobile");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
