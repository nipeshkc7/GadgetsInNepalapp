package com.gadgetsinnepal.gadgetsinnepalapp;

import android.app.FragmentTransaction;
import android.content.Intent;
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

   // android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
    String Tag=null;
    String PrevTag=null;
    String Title="GadgetsInNepal App";
    Fragment frag=null;

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

        android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

        Tag="home";
        frag=getSupportFragmentManager().findFragmentByTag("home");
        Bundle args=new Bundle();
        args.putString("url","http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?page=");
        frag=new homeFragment();
        frag.setArguments(args);
        ft.add(R.id.fragment_container,frag,Tag);
        ft.commit();
        PrevTag=Tag;
        navigationView.setCheckedItem(R.id.nav_home);





        FloatingActionButton fab_search=(FloatingActionButton) findViewById(R.id.fab);
        fab_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
           }
        });
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
        String url="http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?page=";

        int id = item.getItemId();
        android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

        if(getSupportFragmentManager().findFragmentByTag(PrevTag)!=null) {
            Log.w("HIDING",":fragmentWithTag"+getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
            ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
        }

        switch(id){
            case R.id.nav_home:
                Title="Latest Articles";
                Tag="home";
                url="http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?page=";
                break;
            case R.id.nav_tech:
                Title="Tech News";
                Tag="tech";
                url="http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?categories=1&&page=";
                break;
            case R.id.nav_nepal:
                Title="Nepal";
                Tag="nepal";
                url="http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?categories=299&&page=";
                break;
            case R.id.nav_mobile:
                Title="Mobile Prices";
                Tag="mobile";
                url="http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?categories=272&&page=";
                break;
            case R.id.nav_best:
                Title="Best of the Best";
                Tag="best";
                url="http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts?categories=294&&page=";
                break;
            case R.id.nav_howto:
                Title="How To";
                Tag="howto";
                url="http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts?categories=76&&page=";
                break;
            case R.id.nav_pclaptops:
                Title="PC and Laptops";
                Tag="pclaptops";
                url="http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts?categories=707&&page=";
                break;

            case R.id.nav_save:
                //Open up saved articles fragment
                Title="Saved Articles";
                Tag="save";
                frag=getSupportFragmentManager().findFragmentByTag(Tag);

                if(getSupportFragmentManager().findFragmentByTag(PrevTag)!=null) {
                        Log.w("HIDING",":fragmentWithTag"+getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
                        ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                    }
                    frag=new saveFragment();
                    ft.add(R.id.fragment_container,frag,Tag);
                    ft.commit();
                    PrevTag=Tag;

                break;

            default:
                break;
        }

        getSupportActionBar().setTitle(Title);

        if(id==R.id.nav_home||id==R.id.nav_tech||id==R.id.nav_mobile||id==R.id.nav_nepal||id==R.id.nav_pclaptops||id==R.id.nav_howto||id==R.id.nav_best) {
            frag = getSupportFragmentManager().findFragmentByTag(Tag);

            if (frag == null) {
                if (getSupportFragmentManager().findFragmentByTag(PrevTag) != null) {
                    Log.w("HIDING", ":fragmentWithTag" + getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
                    ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                }
                frag = new homeFragment();
                Bundle args = new Bundle();
                args.putString("url", url);
                frag.setArguments(args);
                Log.w("SENDINGURL",""+url);
                ft.add(R.id.fragment_container, frag, Tag);
                ft.commit();
                PrevTag = Tag;
            } else {
                Log.w("HIDING", ":fragmentWithTag" + getSupportFragmentManager().findFragmentByTag(PrevTag));
                ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                ft.show(frag);
                ft.commit();
                PrevTag = Tag;
                Toast.makeText(getApplicationContext(), Tag, Toast.LENGTH_SHORT).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

