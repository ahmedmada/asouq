package com.example.hp.aswaq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubAdapter extends BaseAdapter {
    private Context context;
    ArrayList<String> imglist;
    ArrayList<String> names;

    public SubAdapter(Context context, ArrayList<String> Sub_name, ArrayList<String> S_imgList) {
        this.context = context;
        this.names = Sub_name;
        this.imglist=S_imgList;

    }
    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = new View(context);

        // get layout from mobile.xml
        view = inflater.inflate(R.layout.sub_itemlist, null);

                // set value into textview
                TextView textView =  view
                        .findViewById(R.id.subText);
                textView.setText(names.get(i));

                // set image based on selected text
                ImageView imageView = view
                        .findViewById(R.id.subImg);

                Picasso.with(imageView.getContext())
                        .load(imglist.get(i))
                        .noFade().resize(200, 200)
                        .centerCrop()
                        .into(imageView);

        return view;
    }
}
