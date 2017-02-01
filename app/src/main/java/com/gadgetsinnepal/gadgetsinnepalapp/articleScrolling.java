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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.webkit.WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;

public class articleScrolling extends AppCompatActivity{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        WebView webView=(WebView) findViewById(R.id.articleContent);

        webView.getSettings().setLayoutAlgorithm(TEXT_AUTOSIZING);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        ImageView imageView=(ImageView)findViewById(R.id.featuredImage);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab_note);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.w("ISSHOWING",":"+getSupportActionBar().isShowing());

        Intent intent=getIntent();
//
//        fab.setOnClickListener(new OnClickListener() {
//                                   @Override
//                                   public void onClick(View v) {
//                                       onBackPressed();;
//                                   }
//                               });


                collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //textView.setText(Html.fromHtml(intent.getStringExtra("content"),Html.FROM_HTML_MODE_LEGACY));
            webView.loadDataWithBaseURL("", intent.getStringExtra("content"),"text/html","UTF-8", "");
        }
        Picasso.with(getApplicationContext())
                .load(intent.getStringExtra("imgurl"))
                .placeholder(R.drawable.ggg)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView);
        collapsingToolbarLayout.setTitle(intent.getStringExtra("title"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toolbar.setNavigationIcon(R.drawable.ic_menu_send);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("PRESSEDSEND",":backbutton");
//                Toast.makeText(getApplicationContext(),"PRESSED IT",Toast.LENGTH_LONG).show();
//                onBackPressed();
//            }
//        });

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
