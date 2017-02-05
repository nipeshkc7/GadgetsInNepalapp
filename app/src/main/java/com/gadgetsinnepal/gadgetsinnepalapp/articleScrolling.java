package com.gadgetsinnepal.gadgetsinnepalapp;

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

import static android.webkit.WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
import static android.webkit.WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
import static android.webkit.WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;

public class articleScrolling extends AppCompatActivity{

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        WebView webView=(WebView) findViewById(R.id.articleContent);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setLayoutAlgorithm(SINGLE_COLUMN);
        FloatingActionButton fabShare=(FloatingActionButton)findViewById(R.id.fabShare);

        ImageView imageView=(ImageView)findViewById(R.id.featuredImage);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.w("ISSHOWING",":"+getSupportActionBar().isShowing());

        Intent intent=getIntent();

        fabShare.setOnClickListener(new OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                       sharingIntent.setType("text/plain");
                                       String shareBodyText = "Check it out. Your message goes here http://www.google.com";
                                       sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                                       sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                                       sharingIntent.putExtra(android.content.Intent.EXTRA_EMAIL,shareBodyText);
                                       startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

                                   }
                               });


                collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);



        String htmlString = "<html><head><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script><style>figure {position:relative;padding-left:0;margin-left:0;}iframe{max-width:100%;}html,body{max-width:99%;overflow-x:hidden;display:inline-block;float:left;}</style></head><body>"+intent.getStringExtra("content")+"</body></html>";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                webView.loadDataWithBaseURL("",htmlString ,"text/html","UTF-8", "");
            }else{
                WebSettings settings = webView.getSettings();
                settings.setDefaultTextEncodingName("utf-8");
                webView.loadData(htmlString, "text/html; charset=utf-8",null);

            }
            Picasso.with(getApplicationContext())
                    .load(intent.getStringExtra("imgurl"))
                    .placeholder(R.drawable.ggg)
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageView);
            collapsingToolbarLayout.setTitle(intent.getStringExtra("title"));

   //         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    }
