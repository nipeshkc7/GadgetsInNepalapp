package com.gadgetsinnepal.gadgetsinnepalapp;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
        Log.w("fetching",request_url);
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.w("FetchWpApi","Response is : "+response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                final sItem sitem=new sItem();
                                JSONObject item = (JSONObject) response
                                        .get(i);
                                sitem.id= item.getString("id");
                                sitem.date= item.getString("date");
                                sitem.link= item.getString("link");

                                Log.w("LINK",":"+sitem.link);

                                String content;
                                content=item.getJSONObject("content").getString("rendered");
                                sitem.content=content;
                                JSONObject titleobj = item
                                        .getJSONObject("title");
                                sitem.title= titleobj.getString("rendered");
                                String featuredMedia= item.getString("featured_media");
                                Log.w("FetchWpApiImageno",featuredMedia);
                                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                        "https://www.gadgetsinnepal.com.np/wp-json/wp/v2/media/"+featuredMedia, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject nested_response) {

                                        try {
                                            JSONObject guilld = nested_response.getJSONObject("guid");

                                            //Full sized image::
                                            //String featured_img_url = guilld.getString("rendered");

                                            //thumbnail sized image::
                                            String featured_img_url= nested_response.getJSONObject("media_details").getJSONObject("sizes").getJSONObject("thumbnail").getString("source_url");

                                            //medium sized image
                                            String mediumSizedImage= nested_response.getJSONObject("media_details").getJSONObject("sizes").getJSONObject("medium").getString("source_url");
                                            sitem.mediumSizedImage= mediumSizedImage;
                                            sitem.img=featured_img_url;
                                            Log.w("FetchWpApiImage",featured_img_url);
                                            //ASSIGN VALUES TO LIST HERE


                                            onCallBack.onSuccess(sitem);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
//
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(context,
//                                                "Unable to connect. Some error has occured !"+error.getMessage(), Toast.LENGTH_LONG).show();
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                         //   Toast.makeText(context,"network timeout error",
                                          //          Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                            //TODO
                                        } else if (error instanceof ServerError) {
                                            //TODO
                                        } else if (error instanceof NetworkError) {
                                            //TODO
                                        } else if (error instanceof ParseError) {
                                            //TODO
                                        }

                                        //onCallBack.onFail("Some Error has occured");
                                    }
                                });
                                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                                        20000,
                                        10,          //Retry for image 3 times
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
                        onCallBack.onFail("Volley error");
                    }
                });

                jsArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsArrayRequest);
        //END OF FETCHING API DATA
    }


    public interface Callback{
        void onSuccess(sItem sitem);
        void onFail(String msg);
    }

}




