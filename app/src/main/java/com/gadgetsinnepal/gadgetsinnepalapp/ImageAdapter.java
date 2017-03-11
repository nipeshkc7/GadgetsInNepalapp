package com.gadgetsinnepal.gadgetsinnepalapp;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.gadgetsinnepal.gadgetsinnepalapp.R;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;

        import static com.gadgetsinnepal.gadgetsinnepalapp.R.id.grid_item_image;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    //private final String[] videosList;
    private ArrayList<video_items> list;

    public ImageAdapter(Context context, ArrayList<video_items> list) {
        this.context = context;
        this.list=list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        video_items videoItems;
        videoItems=list.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from grid_design.xml
            gridView = inflater.inflate(R.layout.grid_design, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(videoItems.title);

            // set image based on selected text
            //ImageView imageView = (ImageView) gridView
            //        .findViewById(R.id.grid_item_image);


            Picasso.with(context)
                    .load(videoItems.img)
                    .placeholder(R.drawable.ggg)
                    .error(android.R.drawable.stat_notify_error)
                    .into((ImageView)gridView.findViewById(R.id.grid_item_image));


            //String mobile = videosList[position];


            //    imageView.setImageResource(R.drawable.ggg);


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}