package com.example.hp.aswaq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart_adapter extends BaseAdapter
{
    private Context context;
    ArrayList<String> imgurl;
    ArrayList<String> names;
    ArrayList<String> price;
    ArrayList<String> sale;
    ArrayList<String> id;
    ArrayList<String> Pstock;
    ArrayList<String> Pcount;
    ArrayList<String> Psubtotal;

    int quantity=0;
    int stock=4;
   // TextView count;
 //  public TextView total;
    float tota;
    float Sum=0;
    Cart cart;
    double sum=0;
    private double totalCost = 0;



    SharedPreferences sharedpreferences;




    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    SharedPreferences preferences;


    public Cart_adapter(Context context,   ArrayList<String> imgurl,  ArrayList<String> names,
                                ArrayList<String> price, ArrayList<String> sale, ArrayList<String> id
            ,ArrayList<String> Pstock,ArrayList<String>count,ArrayList<String>subtotal


    ) {
        this.context = context;
        this.names = names;
        this.imgurl=imgurl;
        this.price=price;
        this.sale=sale;
        this.id=id;
        this.Pstock=Pstock;
       this. Pcount=count;
        this.Psubtotal=subtotal;

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
    public long getItemId(int p) {
        return p;
    }

    private class ViewHolder {

        protected TextView count,textView,Cprice,Csale;
        ImageView Cincrease;
        ImageView Cdecrease;
        ImageView Cdelete,CIMG;
       TextView total;

    }


    @Override
     public View getView( final int i, View view,final ViewGroup viewGroup)
   {

     ViewHolder holder = null;

       LayoutInflater mInflater = (LayoutInflater)
               context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
       if (view == null) {
           view = mInflater.inflate(R.layout.cart_itemlist, null);
           holder = new ViewHolder();
           holder.textView = (TextView) view.findViewById(R.id.C_naame);
           holder.count = (TextView) view.findViewById(R.id.C_count);
           holder.Cprice = (TextView) view.findViewById(R.id.C_price);
           holder.Csale = (TextView) view.findViewById(R.id.C_sale);
           holder.Cincrease = (ImageView) view.findViewById(R.id.C_increase);
           holder.Cdecrease = (ImageView) view.findViewById(R.id.c_decrease);
           holder.Cdelete = (ImageView) view.findViewById(R.id.c_delete);
           holder.CIMG = (ImageView) view.findViewById(R.id.C_img);
          holder.total=view.findViewById(R.id.C_total);


           view.setTag(holder);
       }
       else {
           holder = (ViewHolder) view.getTag();


       }

       final TextView titleTextView = holder.count;
      // String count=userDbHelper.getcount(names.get(i));
      // titleTextView.setText(sharedpreferences.getString("value"+i, ""));

//
//       sharedpreferences = context.getSharedPreferences("prefs name", Context.MODE_PRIVATE);
//       if (sharedpreferences.contains("value"+i))
//       {
//               titleTextView.setText(sharedpreferences.getString("value"+i, ""));
//               Pcount.add(i,sharedpreferences.getString("value"+i, ""));
//
//
//       }else{
//           holder.count.setText(String.valueOf(quantity));
//           titleTextView.setText(String.valueOf(quantity));
//
//
//       }
       holder.count.setText(Pcount.get(i));
       holder.textView.setText(names.get(i));
       holder.Cprice.setText(price.get(i));
       holder. Cprice.setPaintFlags(holder.Cprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
       holder.Csale.setText(sale.get(i));
       holder. Csale.setPaintFlags(holder.Csale.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

       quantity=Integer.parseInt(titleTextView.getText().toString());
       tota = Float.parseFloat(titleTextView.getText().toString()) * Float.parseFloat(price.get(i));
       holder.total.setText(Psubtotal.get(i));

       final ViewHolder finalHolder1 = holder;

       userDbHelper=new UserDbHelper(context);
       sqLiteDatabase=userDbHelper.getWritableDatabase();


       holder.Cincrease.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               quantity=Integer.parseInt(titleTextView.getText().toString());

               int valid;
               try{
                    valid=Integer.parseInt(Pstock.get(i));
               }catch (NumberFormatException ex){
                   valid = 1; // default ??
               }
               if(quantity > valid){
                   Build();

               }else{
                   quantity++;

               titleTextView.setText(String.valueOf(quantity));
               String nn=titleTextView.getText().toString();
                   tota = Float.parseFloat(titleTextView.getText().toString()) * Float.parseFloat(price.get(i));
                   finalHolder1.total.setText(String.valueOf(tota));
                   String t=finalHolder1.total.getText().toString();
                   Psubtotal.add(i,t);
                   String idd=id.get(i);
                   String na=names.get(i);
                   String pr=price.get(i);
                   String sa=sale.get(i);
                   String stok=Pstock.get(i);
                   String it_c=Pcount.get(i);
                   String it_s=Psubtotal.get(i);
                   String imge=imgurl.get(i);
//                   for(int f=0;f<Psubtotal.size();f++){
//                       double sum=0;
//                       sum=sum+Double.parseDouble(Psubtotal.get(f).toString());
//                       Log.e("sum",String.valueOf(sum));
//                   }

                   boolean update =userDbHelper.updateInfo(idd,na,imge,pr,sa,stok,it_c,it_s);

                   if(update==true) {
                       // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                       Toast.makeText(context, "Added To Cart  ", Toast.LENGTH_LONG).show();
                   }else{
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                   }
//               SharedPreferences.Editor editor = sharedpreferences.edit();
//               editor.putString("value"+i, nn);
//                   editor.putString("sum",String.valueOf(Sum));
//               editor.apply();
//               Log.e("df"+(i),sharedpreferences.getString("value"+i,""));


               }

           }
       });

       final ViewHolder finalHolder4 = holder;
       holder.Cdecrease.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               quantity=Integer.parseInt(titleTextView.getText().toString());


               if(quantity==1){
                   AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(context);
                   alertDialog1.setTitle(" أسواق الشريف");
                   alertDialog1.setMessage("هل تريد حذف المنتج");


                   alertDialog1.setPositiveButton("موافق",
                           new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int which) {
                                   //dialog.cancel();
                                   String n=names.get(i);
                                   String p=price.get(i);
                                   String im=imgurl.get(i);
                                   String s=sale.get(i);
                                   String t_id=id.get(i);
                                   String t_stock=Pstock.get(i);
                                   String count=Pcount.get(i);
                                   String subt=Psubtotal.get(i);
                                   userDbHelper=new UserDbHelper(context);
                                   sqLiteDatabase=userDbHelper.getWritableDatabase();
                                   Cursor c=  sqLiteDatabase.rawQuery("SELECT *    FROM "+ UserData.NewUserData.TABLE_NAME
                                                   + " WHERE " + UserData.NewUserData.product_name +
                                                   " = " + "'" + n + "'"
                                           , null);

                                   if(c.moveToFirst())
                                   {
                                       boolean delet = userDbHelper.deleteinformation(t_id,n, im, p, s,t_stock,count,subt);

                                       if (delet == true) {

                                           Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
                                       } else {
                                           Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                                       }

                                   }

                                   userDbHelper.close();
                                   imgurl.remove(i);
                                   names.remove(i);
                                   price.remove(i);
                                   sale.remove(i);
                                   notifyDataSetChanged();

                               }
                           });

                   alertDialog1.setNegativeButton("إلغاء",
                           new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.cancel();
                               }
                           });
                   AlertDialog dialog = alertDialog1.create();
                   dialog.show();

//                   AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                   alertDialog.setTitle(" أسواق الشريف");
//                   alertDialog.setMessage("غير متاح");
//
//
//                   alertDialog.setPositiveButton("موافق",
//                           new DialogInterface.OnClickListener() {
//                               public void onClick(DialogInterface dialog, int which) {
//                                   dialog.cancel();
//                               }
//                           });
//
//                   alertDialog.setNegativeButton("إلغاء",
//                           new DialogInterface.OnClickListener() {
//                               public void onClick(DialogInterface dialog, int which) {
//                                   dialog.cancel();
//                               }
//                           });
//                   alertDialog.show();

               }else {
                   quantity--;
                   titleTextView.setText(String.valueOf(quantity));
                   String n = titleTextView.getText().toString();
                   Pcount.add(i,n);

                   tota = Float.parseFloat(titleTextView.getText().toString()) * Float.parseFloat(price.get(i));
                   finalHolder1.total.setText(String.valueOf(tota));
                   String to=finalHolder1.total.getText().toString();
                   Psubtotal.add(i,to);
                   String idd=id.get(i);
                   String na=names.get(i);
                   String pr=price.get(i);
                   String sa=sale.get(i);
                   String stok=Pstock.get(i);
                   String it_c=Pcount.get(i);
                   String it_s=Psubtotal.get(i);
                   String imge=imgurl.get(i);

                   boolean update =userDbHelper.updateInfo(idd,na,imge,pr,sa,stok,it_c,it_s);

                   if(update==true) {
                       // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                       Toast.makeText(context, "Added To Cart  ", Toast.LENGTH_LONG).show();
                       Toast.makeText(context, n+"name"+it_c+"count"+it_s+"sub", Toast.LENGTH_LONG).show();
                   }else{
                       Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                   }

//                   SharedPreferences.Editor editor = sharedpreferences.edit();
//                   editor.putString("value"+i, String.valueOf(quantity));
//                   editor.putString("sum"+i,String.valueOf(Sum));
//                   editor.apply();
               }
           }
       });



       holder.Cdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=names.get(i);
                String p=price.get(i);
                String im=imgurl.get(i);
                String s=sale.get(i);
                String t_id=id.get(i);
                String t_stock=Pstock.get(i);
                String count=Pcount.get(i);
                String subt=Psubtotal.get(i);
                userDbHelper=new UserDbHelper(context);
                sqLiteDatabase=userDbHelper.getWritableDatabase();

                     Cursor c=  sqLiteDatabase.rawQuery("SELECT *    FROM "+ UserData.NewUserData.TABLE_NAME
                                + " WHERE " + UserData.NewUserData.product_name +
                                " = " + "'" + n + "'"
                       , null);

                if(c.moveToFirst())
                {
                    boolean delet = userDbHelper.deleteinformation(t_id,n, im, p, s,t_stock,count,subt);

                    if (delet == true) {

                       // Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }

                }

                userDbHelper.close();



//                SharedPreferences preferences = context.getSharedPreferences("prefs name", Context.MODE_PRIVATE);
//
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.remove("value"+i).apply();
                tota -= Float.parseFloat(titleTextView.getText().toString()) * Float.parseFloat(price.get(i));

                imgurl.remove(i);
                names.remove(i);
                price.remove(i);
                sale.remove(i);

                notifyDataSetChanged();
            }
        });

          Picasso.with(context)
                .load(imgurl.get(i))
                .noFade().resize(150, 200)

                .into(holder.CIMG);

            Log.e("too",String.valueOf(Sum));








       return view;
   }
    public void Build()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(" أسواق الشريف");
        alertDialog.setMessage("هذه الكميه غير متوفره ف المخزون");


        alertDialog.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });

        alertDialog.setNegativeButton("إلغاء",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }


}
