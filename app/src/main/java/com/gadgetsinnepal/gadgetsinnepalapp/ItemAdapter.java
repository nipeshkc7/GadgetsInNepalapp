package com.gadgetsinnepal.gadgetsinnepalapp;


import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private int MAX_CACHE_SIZE=1024 * 1024 * 20;
    private Context context;
    private ArrayList<sItem> ItemList;
    private int lastPosition=-1;

    public ItemAdapter(Context context, ArrayList<sItem> ItemList) {
        this.context = context;
        this.ItemList = ItemList;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.w("ItemAdapter","createdviewholder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext()).inflate(R.layout.final_card, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        sItem item = ItemList.get(position);
        int maxSize = MAX_CACHE_SIZE;

        Picasso.with(context)
                .load(item.img)
                .placeholder(R.drawable.ggg)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImg);

        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(item.title);
        }
//        holder.tvText.setText(item.title);
        holder.tvText.setText(result);

        holder.dateText.setText(dateCalculator(item.date));

        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.animator.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {

        if (ItemList != null) {
            return ItemList.size();
        }
        return 0;
    }

    //Viewholder Class
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cvItem;
        ImageView ivImg;
        TextView tvText;
        TextView dateText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cvItem = (CardView) itemView.findViewById(R.id.cvItem);
            ivImg = (ImageView) itemView.findViewById(R.id.ivMainImage);
            tvText = (TextView) itemView.findViewById(R.id.tvTitle);
            dateText = (TextView) itemView.findViewById(R.id.dateText);
        }

    }

    private String dateCalculator(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        long different;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        Calendar c = Calendar.getInstance();
        // Log.w("CALANDERDATE",":"+c.getTimeInMillis());
        try {
            Date diff_date = format.parse(date);
            different = c.getTimeInMillis() - diff_date.getTime();
            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (elapsedDays == 0) {
                if (elapsedHours == 0) {
                    if (elapsedMinutes == 0) {
                        return elapsedSeconds + "s";
                    }
                    return elapsedMinutes + "m";
                }
                return elapsedHours + "hr";
            }
            return elapsedDays + "d";

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "Date error";
        }

    }
}

