package com.gadgetsinnepal.gadgetsinnepalapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   // android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
    String Tag=null;
    String PrevTag="home";  //used to be null; may cause error
    String ArticleTabTag="home";
    String Title="GadgetsInNepal";
    String CurrentNavDrawerTitle=Title;
    Fragment frag=null;
    int checked_item = 0;
    String search_placeholder = "";
    String search_link="";
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

        /*Initializing Nav Drawer*/
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Initializing Bottom Nav tab*/
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//Creating a home layout fragment
        Tag="home";
        frag=getSupportFragmentManager().findFragmentByTag("home");
        Bundle args=new Bundle();
        args.putString("url",getResources().getText(R.string.gadgets_posts_url)+"?page=");
        frag=new homeFragment();
        frag.setArguments(args);
        ft.add(R.id.fragment_container,frag,Tag);
        ft.commit();
        PrevTag=Tag;
        navigationView.setCheckedItem(R.id.nav_home);

        //For testing new home layout
//        Tag="home";
//        frag=getSupportFragmentManager().findFragmentByTag("home");
//        frag=new latest_staggered_list();
//        ft.add(R.id.fragment_container,frag,Tag);
//        ft.commit();
//        PrevTag=Tag;
//        navigationView.setCheckedItem(R.id.nav_home);




        FloatingActionButton fab_search=(FloatingActionButton) findViewById(R.id.fab);
        fab_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checked_item =getCheckedItem(navigationView);
                search_link="https://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?search=";


                Log.w("CHECKEDITEM", String.valueOf(checked_item));
                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                intent.putExtra("search_placeholder",search_placeholder);
                intent.putExtra("search_link",search_link);
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
            Intent intent=new Intent(getApplicationContext(),settingsActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String url="https://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?page=";

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
                url=getResources().getText(R.string.gadgets_posts_url)+"?page=";
                break;
            case R.id.nav_tech:
                Title="Tech News";
                Tag="tech";
                url=getResources().getText(R.string.gadgets_posts_url)+"?categories="+getResources().getText(R.string.tech_category)+"&&page=";
                break;
            case R.id.nav_nepal:
                Title="Nepal";
                Tag="nepal";
                url=getResources().getText(R.string.gadgets_posts_url)+"?categories="+getResources().getText(R.string.nepal_category)+"&&page=";
                break;
            case R.id.nav_mobile:
                Title="Mobile Prices";
                Tag="mobile";
                url=getResources().getText(R.string.gadgets_posts_url)+"?categories="+getResources().getText(R.string.mobile_price_category)+"&&page=";
                break;
            case R.id.nav_best:
                Title="Best of the Best";
                Tag="best";
                url=getResources().getText(R.string.gadgets_posts_url)+"?categories="+getResources().getText(R.string.best_category)+"&&page=";
                break;
            case R.id.nav_howto:
                Title="How To";
                Tag="howto";
                url=getResources().getText(R.string.gadgets_posts_url)+"?categories="+getResources().getText(R.string.how_to_category)+"&&page=";
                break;
            case R.id.nav_pclaptops:
                Title="PC and Laptops";
                Tag="pclaptops";
                url=getResources().getText(R.string.gadgets_posts_url)+"?categories="+getResources().getText(R.string.pc_category)+"&&page=";
                break;

            case R.id.nav_save:
                //Open up saved articles fragment
                Title = "Saved Articles";
                Tag = "save";
                if(!readFromFile(getApplicationContext()).equals("")) {

                    frag = getSupportFragmentManager().findFragmentByTag(Tag);

                    if (getSupportFragmentManager().findFragmentByTag(PrevTag) != null) {
                        Log.w("HIDING", ":fragmentWithTag" + getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
                        ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                    }
                    frag = new saveFragment();
                    ft.add(R.id.fragment_container, frag, Tag);
                    ft.commit();
                    PrevTag = Tag;

                }else{
                    Toast.makeText(getApplicationContext(),"No articles saved",Toast.LENGTH_SHORT).show();
                    if (getSupportFragmentManager().findFragmentByTag(PrevTag) != null) {
                        Log.w("HIDING", ":fragmentWithTag" + getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
                        ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                    }
                    frag = new noSavedArticles();
                    ft.add(R.id.fragment_container, frag, Tag);
                    ft.commit();
                    PrevTag = Tag;
                }
                break;

            case R.id.nav_videos:
                Intent video_intent= new Intent(getApplicationContext(),grid_videos_list.class);
                video_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(video_intent);
                break;

            //NEED TO ADD LINK TO THE APP
            case R.id.nav_share:

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBodyText = "Check out GadgetsInNepal's app\n" + "<LINK HERE>";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_EMAIL, shareBodyText);
                    startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

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
            //    Toast.makeText(getApplicationContext(), Tag, Toast.LENGTH_SHORT).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        ArticleTabTag= PrevTag;
        CurrentNavDrawerTitle= Title;
        return true;
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("savedArticles.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private int getCheckedItem(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.isChecked()) {
                return i;
            }
        }

        return -1;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav_hom:
                    Title=CurrentNavDrawerTitle;
                    Log.w("Home","clicked");
                    frag = getSupportFragmentManager().findFragmentByTag(ArticleTabTag);
                    ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                    Log.w("HidingFragment",getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
                    Log.w("ShowingFragment",getSupportFragmentManager().findFragmentByTag(ArticleTabTag).getTag());

                    ft.show(frag);
                    ft.commit();
                    PrevTag = ArticleTabTag;
                    getSupportActionBar().setTitle(Title);

                    return true;
                case R.id.nav_vids:
                    Title="Videos";
                    Tag="videos";
                    frag = getSupportFragmentManager().findFragmentByTag(Tag);

                    if (frag == null) {
                        if (getSupportFragmentManager().findFragmentByTag(PrevTag) != null) {
                            Log.w("HIDING", ":fragmentWithTag" + getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
                            ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                        }
                        frag = new youtube_videos();
                        ft.add(R.id.fragment_container, frag, Tag);
                        ft.commit();
                        PrevTag = Tag;
                    } else {
                        Log.w("HIDING", ":fragmentWithTag" + getSupportFragmentManager().findFragmentByTag(PrevTag));
                        ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                        ft.show(frag);
                        ft.commit();
                        PrevTag = Tag;
                        //    Toast.makeText(getApplicationContext(), Tag, Toast.LENGTH_SHORT).show();
                    }

                    Log.w("videos","clicked");
                    getSupportActionBar().setTitle(Title);
                    return true;
                case R.id.nav_sav:
                    Log.w("save","clicked");
                  //  return true;
                //Open up saved articles fragment
                    Title = "Saved Articles";
                    Tag = "save";
                    if(!readFromFile(getApplicationContext()).equals("")) {

                        frag = getSupportFragmentManager().findFragmentByTag(Tag);

                        if (getSupportFragmentManager().findFragmentByTag(PrevTag) != null) {
                            Log.w("HIDING", ":fragmentWithTag" + getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
                            ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                        }
                        frag = new saveFragment();
                        ft.add(R.id.fragment_container, frag, Tag);
                        ft.commit();
                        PrevTag = Tag;

                    }else{
                        Toast.makeText(getApplicationContext(),"No articles saved",Toast.LENGTH_SHORT).show();
                        if (getSupportFragmentManager().findFragmentByTag(PrevTag) != null) {
                            Log.w("HIDING", ":fragmentWithTag" + getSupportFragmentManager().findFragmentByTag(PrevTag).getTag());
                            ft.hide(getSupportFragmentManager().findFragmentByTag(PrevTag));
                        }
                        frag = new noSavedArticles();
                        ft.add(R.id.fragment_container, frag, Tag);
                        ft.commit();
                        PrevTag = Tag;
                    }
                    getSupportActionBar().setTitle(Title);
                    return true;
            }


            return false;
        }

    };

}

