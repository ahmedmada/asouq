package com.example.hp.aswaq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class gridcat_adapter extends BaseAdapter
{
    private Context context;
    ArrayList<String> imglist;
    ArrayList<String> names;

    public gridcat_adapter(Context context,   ArrayList<String> imglist,  ArrayList<String> names) {
        this.context = context;
        this.names = names;
        this.imglist=imglist;

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
        view = inflater.inflate(R.layout.gridcat_item, null);
        for(int y=0;y<names.size();y++)
        {
            if(!(names.get(y).equals("مجلة العروض")))
            {
                // set value into textview
                TextView textView = view
                        .findViewById(R.id.gridTV);
                textView.setText(names.get(i));

                ImageView imageView = view.findViewById(R.id.gridIm);

                Picasso.with(imageView.getContext())
                        .load(imglist.get(i))
                        .noFade().resize(300, 200)
                        .centerCrop()
                        .into(imageView);
            }




        }











        return view;
    }
}
