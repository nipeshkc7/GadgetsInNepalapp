package com.gadgetsinnepal.gadgetsinnepalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;

import static android.R.attr.button;
import static com.gadgetsinnepal.gadgetsinnepalapp.R.id.toolbar;

public class homeFragment extends Fragment {

    private RecyclerView rvItem;
    private updateAdapter updateLatestPage;
    private Button retryButton;
    //private ProgressBar MainProgress;
    private AVLoadingIndicatorView MainProgress;
    private TextView errorMessage;
    public homeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            Bundle args=getArguments();

           final View rootview = inflater.inflate(R.layout.fragment_home, container, false);
            rvItem = (RecyclerView) rootview.findViewById(R.id.rvItem);
            retryButton=(Button)rootview.findViewById(R.id.retryButton);
            //MainProgress=(ProgressBar)rootview.findViewById(R.id.MainProgress);
            MainProgress=(AVLoadingIndicatorView) rootview.findViewById(R.id.MainProgress);
            errorMessage=(TextView)rootview.findViewById(R.id.errorMessage);
            MainProgress.setVisibility(View.VISIBLE);
            rvItem.setHasFixedSize(true);
            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            rvItem.setLayoutManager(manager);
            final String url=args.getString("url");
            updateLatestPage = new updateAdapter(rvItem, getContext(), url, manager,rootview);

            //When the retry button is pressed the page has to reload

            updateLatestPage.fetchAndPut();

            retryButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    //updateLatestPage = new updateAdapter(rvItem, getContext(), url, manager,rootview);
                    updateLatestPage.fetchAndPut();

                    //Make progressbar visible
                    MainProgress.setVisibility(View.VISIBLE);
                    //Make button invisible
                    retryButton.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.GONE);
                }
            });


        return rootview;
        }


}
