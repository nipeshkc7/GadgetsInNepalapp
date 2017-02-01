package com.gadgetsinnepal.gadgetsinnepalapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class mobilePrice extends Fragment {


    private RecyclerView rvItem;
    private updateAdapter updateLatestPage;

    public mobilePrice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //setContentView(R.layout.activity_main);
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        rvItem = (RecyclerView) rootview.findViewById(R.id.rvItem);
        rvItem.setHasFixedSize(true);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvItem.setLayoutManager(manager);
        String url = "http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts/?categories=272&&page=";
        updateLatestPage = new updateAdapter(rvItem, getContext(), url, manager);
        updateLatestPage.fetchAndPut();
        return rootview;

    }
}
