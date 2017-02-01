package com.gadgetsinnepal.gadgetsinnepalapp;


import android.content.Context;
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

/**
 * Created by Sauharda Chhetri on 1/22/2017.
 */

public class FetchWpApi {

    private String url;
    private Context context;
    public FetchWpApi(String url, Context context){
        //CONSTRUCTOR FOR THE CUSTOM CLASS
        this.url=url;
        this.context=context;
    }

    public void fetchApiData(int page,final Callback onCallBack){

        String request_url=url+page;

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                final sItem sitem=new sItem();
                                JSONObject item = (JSONObject) response
                                        .get(i);
                                String id = item.getString("id");
                                sitem.id=id;
                                String date = item.getString("date");
                                String content;
                                content=item.getJSONObject("content").getString("rendered");
                                sitem.content=content;
                                JSONObject titleobj = item
                                        .getJSONObject("title");
                                final String title= titleobj.getString("rendered");
                                sitem.title=title;
                                String featuredMedia= item.getString("featured_media");

                                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                        "http://www.gadgetsinnepal.com/wp-json/wp/v2/media/"+featuredMedia, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject nested_response) {

                                        try {
                                            JSONObject guilld = nested_response.getJSONObject("guid");
                                            String featured_img_url = guilld.getString("rendered");
                                            sitem.img=featured_img_url;
                                            //ASSIGN VALUES TO LIST HERE


                                            onCallBack.onSuccess(sitem);
                                            //Toast.makeText(getApplicationContext(), "IMAGE is :"+featured_img_url,Toast.LENGTH_LONG).show();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context,
                                                    "Error: " + e.getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context,
                                                "ERROR "+error.getMessage(), Toast.LENGTH_LONG).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Toast.makeText(context,"network timeout error",
                                                    Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                            //TODO
                                        } else if (error instanceof ServerError) {
                                            //TODO
                                        } else if (error instanceof NetworkError) {
                                            //TODO
                                        } else if (error instanceof ParseError) {
                                            //TODO
                                        }
                                    }
                                });
                                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                                        7000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });
                jsArrayRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsArrayRequest);
        //END OF FETCHING API DATA
    }


    public interface Callback{
        void onSuccess(sItem sitem);
        void onFail(String msg);
    }

}




