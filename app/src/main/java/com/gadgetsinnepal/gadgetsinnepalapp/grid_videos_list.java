package com.gadgetsinnepal.gadgetsinnepalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class grid_videos_list extends AppCompatActivity {
    GridView gridView;

    static final String[] some_list = new String[] {
            "Android", "iOS","Windows", "Blackberry" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_videos_list);
        gridView = (GridView) findViewById(R.id.activity_grid_videos_list);

        fetchYoutubeData(new videoCallback() {
            @Override
            public void onSuccess(final ArrayList<video_items> list) {
                //set the list into the adapter here

                Log.d("LISTSIZE","is"+list.size());
                gridView.setAdapter(new ImageAdapter(getApplicationContext(), list));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
//                        Toast.makeText(
//                                getApplicationContext(),
//                                ((TextView) v.findViewById(R.id.grid_item_label))
//                                        .getText(), Toast.LENGTH_SHORT).show();
//
//                        Log.w("listitem",(list.get(0)).id);
                        Intent video_intent= new Intent(getApplicationContext(),videos.class);
                        video_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        video_intent.putExtra("videoId",(list.get(position)).id);
                        getApplicationContext().startActivity(video_intent);

                    }
                });

            }

            @Override
            public void onFail(String msg) {

            }
        });




    }

    public void fetchYoutubeData(final videoCallback onvideoCallback){
        String request_url="https://www.googleapis.com/youtube/v3/search?key=AIzaSyCx4JBH-heN_MDS-Gxc2HspQb7cS5nLow8&channelId=UCjcKULZUBw62jQwjT31XWFQ&part=snippet,id&order=date&maxResults=20&type=video";
        final ArrayList<video_items> list=new ArrayList<>();
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, request_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray jsonArray=response.getJSONArray("items");


//                            int maxLogSize = 1000;
//                            for(int i = 0; i <= jsonArray.toString().length() / maxLogSize; i++) {
//                                int start = i * maxLogSize;
//                                int end = (i+1) * maxLogSize;
//                                end = end > jsonArray.toString().length() ? jsonArray.toString().length() : end;
//                                Log.v("JSONARRAY", jsonArray.toString().substring(start, end));
//                            }
                            // Parsing json array response
                            // loop through each json object

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
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),
                                "Cannot connect",
                                Toast.LENGTH_LONG).show();

                    }
                });
      //  jsArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
      //          DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
      //          DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsArrayRequest);


    }

    public interface videoCallback{
        void onSuccess(ArrayList<video_items> list);
        void onFail(String msg);
    }

}
