package com.gadgetsinnepal.gadgetsinnepalapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class youtube_videos extends Fragment {

    private GridView gridView;
    public youtube_videos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_youtube_videos, container, false);
        gridView = (GridView) rootview.findViewById(R.id.grid_videos_list);

        fetchYoutubeData(new grid_videos_list.videoCallback() {
            @Override
            public void onSuccess(final ArrayList<video_items> list) {
                //set the list into the adapter here

                Log.d("LISTSIZE","is"+list.size());
                gridView.setAdapter(new ImageAdapter(getContext(), list));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        Intent video_intent= new Intent(getContext(),videos.class);
                        video_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        video_intent.putExtra("videoId",(list.get(position)).id);
                        getContext().startActivity(video_intent);

                    }
                });

            }

            @Override
            public void onFail(String msg) {

            }
        });

        return rootview;
    }

    public void fetchYoutubeData(final grid_videos_list.videoCallback onvideoCallback){
        String request_url="https://www.googleapis.com/youtube/v3/search?key=AIzaSyCx4JBH-heN_MDS-Gxc2HspQb7cS5nLow8&channelId=UCjcKULZUBw62jQwjT31XWFQ&part=snippet,id&order=date&maxResults=20&type=video";
        final ArrayList<video_items> list=new ArrayList<>();
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, request_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray=response.getJSONArray("items");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                video_items videoItems = new video_items();
                                JSONObject item = (JSONObject) jsonArray.get(i);
                                JSONObject idObject=item.getJSONObject("id");
                                String videoID=idObject.getString("videoId");
                                Log.d("VideoId", videoID);
                                JSONObject snippet=item.getJSONObject("snippet");
                                String title=snippet.getString("title");
                                String thumbnail=snippet.getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                                //Log.d("VideoId","Nothing");
                                Log.d("Count","ing"+title);
                                Log.d("IMG","ing"+thumbnail);

                                videoItems.img=thumbnail;
                                videoItems.title=title;
                                videoItems.id=videoID;
                                list.add(videoItems);
                            }
                            Log.d("Listsize",""+list.size());
                            onvideoCallback.onSuccess(list);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getContext(),
                                "Cannot connect",
                                Toast.LENGTH_LONG).show();

                    }
                });
        //  jsArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
        //          DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        //          DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(jsArrayRequest);


    }

    public interface videoCallback{
        void onSuccess(ArrayList<video_items> list);
        void onFail(String msg);
    }


}
