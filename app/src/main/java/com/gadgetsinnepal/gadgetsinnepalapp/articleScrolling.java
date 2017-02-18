package com.gadgetsinnepal.gadgetsinnepalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.webkit.WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
import static android.webkit.WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
import static android.webkit.WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;

@SuppressWarnings("deprecation")
public class articleScrolling extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        WebView webView=(WebView) findViewById(R.id.articleContent);
        webView.getSettings().setJavaScriptEnabled(true);
        FloatingActionButton fabShare=(FloatingActionButton)findViewById(R.id.fabShare);
        FloatingActionButton fabSave=(FloatingActionButton)findViewById(R.id.fabSave);
        ImageView imageView=(ImageView)findViewById(R.id.featuredImage);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent=getIntent();

        final String title=intent.getStringExtra("title");
        final String link=intent.getStringExtra("link");
        final String content=intent.getStringExtra("content");
        final String featured_img=intent.getStringExtra("imgurl");
        final String date=intent.getStringExtra("date");

        fabShare.setOnClickListener(new OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                       sharingIntent.setType("text/plain");
                                       String shareBodyText = "Check out this article from GadgetsInNepal :"+title+"\n"+ link;

                                       sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                                       sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                                       sharingIntent.putExtra(android.content.Intent.EXTRA_EMAIL,shareBodyText);
                                       startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

                                   }
                               });

        fabSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String old=readFromFile(getApplicationContext());
                Boolean isAlreadySaved=false;
                if(!old.equals("")) {
                    try {
                        JSONArray testingArr = new JSONArray(old);
                        for (int i = 0; i < testingArr.length(); i++) {
                            JSONObject jsonObj = (JSONObject) testingArr.get(i);
                            Log.w("current",""+link);
                            Log.w("TWstinlink",""+jsonObj.getString("link"));
                            if (jsonObj.getString("link").equals(link)) {
                                isAlreadySaved = true;
                                Log.w("FOUNDSAMELINK",":YES");
                                break;
                            }
                            isAlreadySaved = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(!isAlreadySaved) {
                    JSONArray obj = createJSON(old, title, content, link, featured_img, date);
                    writeToFile(obj.toString());
                    Toast.makeText(getApplicationContext(), "Saved Article", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Already Saved", Toast.LENGTH_SHORT).show();
                }
            }
            });


                collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        Spanned result;
        String htmlString = "<html><head><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script><style>figure {position:relative;padding-left:0;margin-left:0;}iframe{max-width:100%;}html,body{max-width:99%;overflow-x:hidden;display:inline-block;float:left;}</style></head><body>"+content+"</body></html>";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                webView.loadDataWithBaseURL("",htmlString ,"text/html","UTF-8", "");
                result = Html.fromHtml(intent.getStringExtra("title"),Html.FROM_HTML_MODE_LEGACY);

        }else{
                WebSettings settings = webView.getSettings();
                settings.setDefaultTextEncodingName("utf-8");
                webView.loadData(htmlString, "text/html; charset=utf-8",null);
                result = Html.fromHtml(intent.getStringExtra("title"));
            }
            Picasso.with(getApplicationContext())
                    .load(featured_img)
                    .placeholder(R.drawable.ggg)
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageView);
            collapsingToolbarLayout.setTitle(result);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl(" javascript:$('img').removeAttr('width');$('img').removeAttr('height');$('figure').removeAttr('style');");

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

    public JSONArray createJSON(String old,String title,String content,String link,String featured_img,String date){

        try {
            JSONArray jsonArray;
            if(old.equals("")){
                jsonArray= new JSONArray();
            }else {
                jsonArray = new JSONArray(old);
            }

            JSONObject obj=new JSONObject();
            obj.put("title",title);
            obj.put("content",content);
            obj.put("link",link);
            obj.put("featured_img",featured_img);
            obj.put("date",date);

            jsonArray.put(obj);

            return jsonArray;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("RETURNING:","NULL");
            return null;

        }
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("savedArticles.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
