package com.gadgetsinnepal.gadgetsinnepalapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;


public class homeFragment extends Fragment {

    private RecyclerView rvItem;
    private updateAdapter updateLatestPage;

    public homeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            //setContentView(R.layout.activity_main);
            Toast.makeText(getContext(),"NOPE!",Toast.LENGTH_LONG).show();
            setRetainInstance(true);
            View rootview = inflater.inflate(R.layout.fragment_home, container, false);
            rvItem = (RecyclerView) rootview.findViewById(R.id.rvItem);
            rvItem.setHasFixedSize(true);

            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            rvItem.setLayoutManager(manager);
            String url = "http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?page=";
            updateLatestPage = new updateAdapter(rvItem, getContext(), url, manager);
            updateLatestPage.fetchAndPut();
            return rootview;
        }


}
