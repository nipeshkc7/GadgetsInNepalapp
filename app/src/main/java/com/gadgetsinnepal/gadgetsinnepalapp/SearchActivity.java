package com.gadgetsinnepal.gadgetsinnepalapp;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SearchView searchView=(SearchView) findViewById(R.id.searchview);
        //SearchView search = (SearchView) item.getActionView();
       //searchView.setLayoutParams(new .LayoutParams(Gravity.RIGHT));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                                              @Override
                                              public boolean onQueryTextSubmit(String query){
                                                  Log.w("SUBMITTED",""+query);
                                                  android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                                                  Bundle bundle=new Bundle();
                                                  bundle.putString("sQuery",query);

                                                  searchContaining frag=new searchContaining();
                                                  frag.setArguments(bundle);
                                                  ft.replace(R.id.fragment_container,frag,"Search");
                                                  ft.commit();
                                                  return false;
                                              }

                                              @Override
                                              public boolean onQueryTextChange(String newText) {
                                                  Log.w("QUERY",""+newText);
                                                  return false;
                                              }
                                          }
        );

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}

