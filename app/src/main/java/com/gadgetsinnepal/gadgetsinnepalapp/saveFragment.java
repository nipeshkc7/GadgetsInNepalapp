package com.gadgetsinnepal.gadgetsinnepalapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class saveFragment extends Fragment {

    private RecyclerView rvItem;


    public saveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String savedData;
        final ArrayList<sItem> list = new ArrayList<>();
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        rvItem = (RecyclerView) rootview.findViewById(R.id.rvItem);
        rvItem.setHasFixedSize(true);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvItem.setLayoutManager(manager);
        //retreive save list
        savedData=readFromFile(getContext());
        Log.w("this",""+savedData);
        try {
            JSONArray jsonArray=new JSONArray(savedData);
            Log.w("JSONRECCC",""+jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                sItem sitem = new sItem();
                //JSONObject jsonObj = new JSONObject(savedData);
                JSONObject jsonObj=(JSONObject) jsonArray.get(i);
                sitem.title = jsonObj.getString("title");
                sitem.content = jsonObj.getString("content");
                sitem.img = jsonObj.getString("featured_img");
                sitem.link = jsonObj.getString("link");
                sitem.date = jsonObj.getString("date");
                list.add(sitem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ItemAdapter adapter = new ItemAdapter(getContext(), list);
        rvItem.setAdapter(adapter);
        rvItem.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rvItem ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent= new Intent(getContext(),articleScrolling.class);
                        intent.putExtra("title",list.get(position).title);
                        intent.putExtra("imgurl",list.get(position).img);
                        intent.putExtra("content",list.get(position).content);
                        intent.putExtra("link",list.get(position).link);
                        intent.putExtra("date",list.get(position).date);
                        Log.w("DATESENT",""+list.get(position).date);
                        getContext().startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        return rootview;

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

}
