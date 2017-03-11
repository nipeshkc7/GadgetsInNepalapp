package com.gadgetsinnepal.gadgetsinnepalapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class searchContaining extends Fragment {

    private RecyclerView rvItem;
    private updateAdapter updateLatestPage;
    private Button shareButton;

    public searchContaining() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String query=getArguments().getString("sQuery");
        String search_placeholder=getArguments().getString("search_placeholder");
        String search_link=getArguments().getString("search_link");
        Log.w("sQueryis",query);
        //setContentView(R.layout.activity_main);
        //Toast.makeText(getContext(),"NOPE!",Toast.LENGTH_LONG).show();
        //setRetainInstance(true);
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        rvItem = (RecyclerView) rootview.findViewById(R.id.rvItem);
        rvItem.setHasFixedSize(true);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvItem.setLayoutManager(manager);
        //String url = "http://www.gadgetsinnepal.com.np/wp-json/wp/v2/posts?search="+query+"&&page=";
        String url = search_link+query+"&&page=";
        Log.w("SENDINGTO",search_link);
        updateLatestPage = new updateAdapter(rvItem, getContext(), url, manager,rootview);
        updateLatestPage.fetchAndPut();
        return rootview;

}
}
