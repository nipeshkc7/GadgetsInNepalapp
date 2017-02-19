package com.gadgetsinnepal.gadgetsinnepalapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class settingsActivity extends AppCompatActivity {
    private ArrayAdapter<String> listAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView mainListView = (ListView) findViewById( R.id.mainListView );
        String[] listItems = new String[] { "Delete Saved Articles"};
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, listItems);
        //TO ADD MORE ELEMENTS DO listadapter.add();
        mainListView.setAdapter(listAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                    String title= (String) parent.getItemAtPosition(position);
                    if(title.equals("Delete Saved Articles")){
                        File dir = getFilesDir();
                        File file = new File(dir, "savedArticles.txt");
                        boolean deleted = file.delete();
                        if(deleted) {
                            Toast.makeText(getBaseContext(), "Saved Articles deleted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getBaseContext(), "Unable to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });


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
