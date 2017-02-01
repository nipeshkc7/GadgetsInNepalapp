package com.gadgetsinnepal.gadgetsinnepalapp;


import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sauharda Chhetri on 1/19/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<sItem> ItemList;

    public ItemAdapter(Context context, ArrayList<sItem> ItemList){
        this.context=context;
        this.ItemList=ItemList;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.from(parent.getContext()).inflate(R.layout.final_card,parent,false);
        ItemViewHolder itemViewHolder=new ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        sItem item= ItemList.get(position);
        Picasso.with(context)
                .load(item.img)
                .placeholder(R.drawable.ggg)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImg);

        holder.tvText.setText(item.title);

    }



    @Override
    public int getItemCount() {

        if(ItemList!=null){
            return ItemList.size();
        }
        return 0;
    }
    //Viewholder Class
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public CardView cvItem;
        public ImageView ivImg;
        public TextView tvText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cvItem=(CardView)itemView.findViewById(R.id.cvItem);
            ivImg=(ImageView)itemView.findViewById(R.id.ivMainImage);
            tvText=(TextView)itemView.findViewById(R.id.tvTitle);

        }

    }

}

