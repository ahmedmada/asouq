package com.example.hp.aswaq;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class favourit_adapter extends BaseAdapter
{
    private Context context;
    ArrayList<String> imgurl;
    ArrayList<String> names;
    ArrayList<String> price;
    ArrayList<String> sale;
    int quantity=1;
    TextView count;

    UserDbHelperFav userDbHelperfav;
    SQLiteDatabase sqLiteDatabase;
    SharedPreferences preferences;


    public favourit_adapter(Context context,   ArrayList<String> imgurl,  ArrayList<String> names,
                        ArrayList<String> price, ArrayList<String> sale


    ) {
        this.context = context;
        this.names = names;
        this.imgurl=imgurl;
        this.price=price;
        this.sale=sale;

    }
    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView( final  int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = new View(context);
        // get layout from mobile.xml
        view = inflater.inflate(R.layout.favorit_itemlist, null);
        TextView FavName =  view.findViewById(R.id.F_naame);
        TextView FavPrice= view.findViewById(R.id.F_price);
        FavName.setText(names.get(i));
        FavPrice.setText(price.get(i)+"ج");
        TextView Csale= view.findViewById(R.id.F_sale);

        if(!(sale.get(i)==" ")){
            Csale.setText(sale.get(i)+"ج");
            FavPrice.setPaintFlags(FavName.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            Csale.setPaintFlags(Csale.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        }

        ImageButton Fdelete = view.findViewById(R.id.F_delete);
       ImageButton Fcart=view.findViewById(R.id.F_cart);



        Fdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=names.get(i);
                String p=price.get(i);
                String im=imgurl.get(i);
                String s=sale.get(i);
                userDbHelperfav=new UserDbHelperFav(context);
                sqLiteDatabase=userDbHelperfav.getWritableDatabase();
               // Toast.makeText(context, "delet", Toast.LENGTH_SHORT).show();

                Cursor c=  sqLiteDatabase.rawQuery("SELECT *    FROM "+ UserData.NewUserData.FavTable_NAME
                                + " WHERE " + UserData.NewUserData.Fav_name +
                                " = " + "'" + n + "'"
                        , null);

                if(c.moveToFirst())
                {
                    boolean delet = userDbHelperfav.deleteinformation(n, im, p, s);

                    if (delet == true) {
                        // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                       // Toast.makeText(context, "One Row deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }

                }
                userDbHelperfav.close();

                imgurl.remove(i);
                names.remove(i);
                price.remove(i);
                sale.remove(i);
                notifyDataSetChanged();


            }
        });






        // set image based on selected text
        ImageView CIMG = view.findViewById(R.id.F_img);

        Picasso.with(context)
                .load(imgurl.get(i))
                .noFade().resize(200, CIMG.getHeight())

                .into(CIMG);

        return view;

    }
}
