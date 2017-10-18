package com.gadgetsinnepal.gadgetsinnepalapp;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

//THIS CLASS UPDATES THE RECYCLER VIEW

public class updateAdapter{
    private FetchWpApi fetch;

    private int totalItemCount;
    private int prevLastvisible=0;
    private int fpage=1;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<sItem> list= new ArrayList<>();
    private LinearLayoutManager manager;
    private ProgressBar progressBar;
    //private ProgressBar MainProgressBar;
    private AVLoadingIndicatorView MainProgressBar;
    private Button retryButton;
    private TextView errorMessage;
    //CONSTRUCTOR FOR updateAdapter
    public updateAdapter(RecyclerView recyclerView, final Context context, String url, LinearLayoutManager manager, View rootview){
        this.context=context;
        this.recyclerView=recyclerView;
        fetch=new FetchWpApi(url,context);
        this.manager=manager;
        progressBar=(ProgressBar)rootview.findViewById(R.id.progress);
//        MainProgressBar=(ProgressBar)rootview.findViewById(R.id.MainProgress);
        MainProgressBar=(AVLoadingIndicatorView) rootview.findViewById(R.id.MainProgress);

        retryButton=(Button)rootview.findViewById(R.id.retryButton);
        errorMessage=(TextView)rootview.findViewById(R.id.errorMessage);
    }

    public void fetchAndPut()
    {
        if(recyclerView.getAdapter()==null) {
            fetch.fetchApiData(fpage, new FetchWpApi.Callback() {
                @Override
                public void onSuccess(sItem sitem) {
                    list.add(sitem);
                    if (list.size() == 1 || recyclerView.getAdapter() == null) {
                        Log.w("updateAdapter","newSITEM"+sitem.toString());
                        ItemAdapter adapter = new ItemAdapter(context, list);
                        recyclerView.setAdapter(adapter);

                        Log.w("FIRST fetch", "this is the first with size" + list.size());
                    } else if (list.size() > 1 && recyclerView.getAdapter() != null) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                        recyclerView.getAdapter().notifyItemRangeChanged(0, recyclerView.getAdapter().getItemCount());
                    }
                    MainProgressBar.setVisibility(View.GONE);
                }
                @Override
                public void onFail(String msg) {
                        MainProgressBar.setVisibility(View.GONE);
                        retryButton.setVisibility(View.VISIBLE);
                        errorMessage.setVisibility(View.VISIBLE);

                }
            });
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent= new Intent(context,articleScrolling.class);
                        intent.putExtra("title",list.get(position).title);
                        //intent.putExtra("imgurl",list.get(position).img);
                        //To use the medium sized image instead of the thumbnail
                        intent.putExtra("imgurl",list.get(position).mediumSizedImage);

                        intent.putExtra("content",list.get(position).content);
                        intent.putExtra("link",list.get(position).link);
                        intent.putExtra("date",list.get(position).date);
                        Log.w("DATESENT",""+list.get(position).date);
                        context.startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        //SCROLL LISTENER ATTACHED TO THE RECYCLER VIEW
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                   totalItemCount = manager.getItemCount();
                    int lastVisibleItemPosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();

                    Log.w("LASTPOS:",":"+lastVisibleItemPosition);
                    Log.w("prevLastvisible",":"+prevLastvisible);
                    Log.w("TotalItemCount",":"+totalItemCount);
                    Log.w("FpAge",":"+fpage);

                        if( (lastVisibleItemPosition+1)==totalItemCount && totalItemCount%10==0 && lastVisibleItemPosition>prevLastvisible)

                        {

                            //SET VISIBILITY OF THE PROGRESS BAR IN THE LAST CARD ITEM TO VISIBLE ??

                            progressBar.setVisibility(View.VISIBLE);
                            fpage++;
                            //loading = false;
                            Log.v("...", "Last Item !");
                            //Do pagination.. i.e. fetch new data
                            prevLastvisible=lastVisibleItemPosition;
                            fetch.fetchApiData(fpage,new FetchWpApi.Callback(){
                                @Override
                                public void onSuccess(sItem sitem){
                                    progressBar.setVisibility(View.GONE);
                                    list.add(sitem);
                                    recyclerView.getAdapter().notifyDataSetChanged();
                                    recyclerView.getAdapter().notifyItemRangeChanged(0, recyclerView.getAdapter().getItemCount());

                                }
                                @Override
                                public void onFail(String msg){
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(context,"Unable to load more content",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                }
            }
        });

    }

}
