package com.example.hp.aswaq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.DropBoxManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class itemsListviewAdapter extends BaseAdapter {

    private Context context;
    int p=0;
    ArrayList<String> imgurl;
    ArrayList<String> names;
    ArrayList<String> price;
    ArrayList<String> sale;
    ArrayList<String> stock;
    ArrayList<String> item_id;
    ArrayList<String> item_c;
    ArrayList<String> item_s;

    ArrayList<HashMap<String, String>> mylist;

    Button AddToCart;
    Button AddTofav;
    UserDbHelper userDbHelper;
    UserDbHelperFav userDbHelperFav;
    SQLiteDatabase sqLiteDatabase;
    boolean flag=false;
    SharedPreferences sharedpreferences;



    public itemsListviewAdapter(Context context,   ArrayList<String> imgurl,  ArrayList<String> names,
                                ArrayList<String> price, ArrayList<String> sale,ArrayList<String> item_id,
                                ArrayList<String> stock


            ) {
        this.context = context;
        this.names = names;
        this.imgurl=imgurl;
        this.price=price;
        this.sale=sale;
        this.item_id=item_id;
        this.stock=stock;
        item_c=new ArrayList<>();
        item_s=new ArrayList<>();
        for(int x=0;x<names.size();x++){
            item_c.add(x,String.valueOf(1));
            item_s.add(x,String.valueOf(0));
        }


    }
    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int p) {
        return p;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = new View(context);
        // get layout from mobile.xml
        view = inflater.inflate(R.layout.custom_item_row, null);


        // set value into textview
        TextView textView =  view.findViewById(R.id.title);
        textView.setText(names.get(i));

        TextView Tprice= view.findViewById(R.id.price);
       Tprice.setText(price.get(i)+"ج");



        TextView Tsale= view.findViewById(R.id.sale);
       if (!(sale.get(i).equals(" "))){
           Tsale.setText(sale.get(i)+"ج");
           Tprice.setPaintFlags(Tprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
           Tsale.setPaintFlags(Tsale.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
       }





        // set image based on selected text
        ImageView itemIMG = view.findViewById(R.id.itemImg);

        Picasso.with(context)
                .load(imgurl.get(i))
                .noFade().resize( itemIMG.getWidth(), 450)

                .into(itemIMG);

        final ImageView  Fav=view.findViewById(R.id.favImg);
        sharedpreferences = context.getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if (sharedpreferences.contains("flage"))
        {
            boolean check = sharedpreferences.getBoolean("flage", flag);
            if (check) {
                Fav.setImageResource(R.drawable.imgo);

            } else {
                Fav.setImageResource(R.drawable.img);
            }
        }


        Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ho"+i, Toast.LENGTH_SHORT).show();
                ///////////////////////////////////////
                userDbHelperFav=new UserDbHelperFav(context);
                sqLiteDatabase=userDbHelperFav.getWritableDatabase();
                String  name=names.get(i);
                String  img=imgurl.get(i);
                String pr=price.get(i);
                String sal=sale.get(i);
                String id=item_id.get(i);

                sharedpreferences = context.getApplicationContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);

                if(flag)
                {
                    Fav.setImageResource(R.drawable.imgo);
                    flag=false;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("flage", flag);
                    editor.apply();
                    editor.commit();
                    boolean delete =userDbHelperFav.deleteinformation(name,img,pr,sal);

                    if(delete){
                        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Fav.setImageResource(R.drawable.img);
                    flag=true;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("flage", flag);
                    editor.apply();
                    editor.commit();
                    Cursor c=  sqLiteDatabase.rawQuery("SELECT *    FROM "+ UserData.NewUserData.FavTable_NAME
                                    + " WHERE " + UserData.NewUserData.Fav_name +
                                    " = " + "'" + name + "'"
                            , null);

                    if(c.moveToFirst())
                    {
                        boolean update =userDbHelperFav.updateInfo(name,img,pr,sal);

                        if(update==true) {
                            // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                            // Toast.makeText(context, "One Row updated", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        boolean insert =userDbHelperFav.AddInfo(name,img,pr,sal);

                        if(insert==true) {
                            // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                            // Toast.makeText(context, "One Row Saved", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                        }


                    }


                }
                userDbHelperFav.close();


            }
        });
         AddToCart=view.findViewById(R.id.addTocart);
        mylist = new ArrayList<HashMap<String, String>>();

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ///////////////////////////////////////
                userDbHelper=new UserDbHelper(context);
                sqLiteDatabase=userDbHelper.getWritableDatabase();
              String  name=names.get(i);
              String  img=imgurl.get(i);
              String pr=price.get(i);
              String sal=sale.get(i);
              String id=item_id.get(i);
              String stok=stock.get(i);
              String it_c=item_c.get(i);
              String it_s=item_s.get(i);





                Cursor c=  sqLiteDatabase.rawQuery("SELECT *    FROM "+ UserData.NewUserData.TABLE_NAME
                                + " WHERE " + UserData.NewUserData.product_name +
                                " = " + "'" + name + "'"
                       , null);

                if(c.moveToFirst())
                {
                    boolean update =userDbHelper.updateInfo(id,name,img,pr,sal,stok,it_c,it_s);

                    if(update==true) {
                        // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "Added To Cart  ", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }
                }else {
                    boolean insert =userDbHelper.AddInfo(id,name,img,pr,sal,stok,it_c,it_s);

                    if(insert==true) {
                        // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                       // Toast.makeText(context, "One Row Saved", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }


                }
                userDbHelper.close();







            }

        });
//
//



        return view;


    }
//    public void AddItem(){
//
//        String item=spinner.getSelectedItem().toString();
//        String count=ScreenTV.getText().toString();
//
//        userDbHelper=new UserDbHelper(context);
//        sqLiteDatabase=userDbHelper.getWritableDatabase();
//
//        Cursor c=  sqLiteDatabase.rawQuery("SELECT *    FROM "+ UserData.NewUserData.TABLE_NAME + " WHERE " + UserData.NewUserData.USER_ITEM +
//                " = " + "'" + item + "'", null);
//
//        if(c.moveToFirst())
//        {
//            boolean update =userDbHelper.updateInfo(item,count);
//
//            if(update==true) {
//                // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
//                Toast.makeText(getBaseContext(), "One Row updated", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
//            }
//        }else {
//            boolean insert =userDbHelper.AddInfo(item,count);
//
//            if(insert==true) {
//                // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
//                Toast.makeText(getBaseContext(), "One Row Saved", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
//            }
//
//
//        }
//        userDbHelper.close();
//
//    }



}
