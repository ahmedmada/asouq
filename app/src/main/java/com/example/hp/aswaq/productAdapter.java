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
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.aswaq.model.CardModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class productAdapter extends BaseAdapter
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


    int quantity=1;
    int stock=4;
    // TextView count;
    //  public TextView total;
    float tota;
    float Sum=0;
    Cart cart;
    private double totalCost = 0;


    SharedPreferences sharedpreferences;




    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    SharedPreferences preferences;

    Cursor cursor;
    CardModel cardModel;
    ArrayList<CardModel> cardModels;

    public void getSelected(){
        userDbHelper=new UserDbHelper(context);

        cursor=userDbHelper.GetData(sqLiteDatabase);
        if(cursor.moveToFirst())
        {
            do {
                cardModel = new CardModel();
                int id,count;
                id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_id)));
                count = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_count)));

                cardModel.setId(id);
                cardModel.setCount(count);

                Log.v("xxxxxxxxxxxxxxxx","id = "+id+" , count = "+count);
                cardModels.add(cardModel);

            } while (cursor.moveToNext());
        }
    }




    public productAdapter(Context context, ArrayList<String> imgurl, ArrayList<String> names,
                        ArrayList<String> price, ArrayList<String> sale, ArrayList<String> id, ArrayList<String> Pstock

    ) {
        this.context = context;
        this.names = names;
        this.imgurl=imgurl;
        this.price=price;
        this.sale=sale;
        this.id=id;
        this.Pstock=Pstock;
       Pcount=new ArrayList<>();
        Psubtotal=new ArrayList<>();
        for(int x=0;x<names.size();x++){
            Pcount.add(x,String.valueOf(1));
            Psubtotal.add(x,String.valueOf(0));
        }

        cardModels = new ArrayList<>();
        getSelected();
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////



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
        ImageView Cdelete,CIMG,cart,sold;
        TextView total;
        LinearLayout linearLayout;
        TextView notify;




    }
    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.products, null);

            holder = new productAdapter.ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.C_naame);

            holder.count = (TextView) view.findViewById(R.id.C_count);
            holder.Cprice = (TextView) view.findViewById(R.id.C_price);
            holder.Csale = (TextView) view.findViewById(R.id.C_sale);
            holder.Cincrease = (ImageView) view.findViewById(R.id.C_increase);
            holder.Cdecrease = (ImageView) view.findViewById(R.id.c_decrease);
            holder.CIMG = (ImageView) view.findViewById(R.id.C_img);
            holder.total=view.findViewById(R.id.C_total);
            holder.cart=view.findViewById(R.id.caaaar);
            holder.linearLayout=view.findViewById(R.id.lin);
           // holder.sold=view.findViewById(R.id.soldoutIMG);
            holder.notify=view.findViewById(R.id.sold);


            view.setTag(holder);
        }
        else {
            holder = (productAdapter.ViewHolder) view.getTag();
        }
        final TextView titleTextView = holder.count;

        for (int j = 0 ; j < cardModels.size() ; j++){
            if (Integer.parseInt(id.get(i)) == cardModels.get(j).getId()){
                holder.count.setText(cardModels.get(j).getCount());
            }
        }

//        sharedpreferences = context.getSharedPreferences("prefs name", Context.MODE_PRIVATE);
//        if (sharedpreferences.contains("value"+i))
//        {
//
//            Log.e("v", sharedpreferences.getString("value"+i, ""));
//
//            titleTextView.setText(sharedpreferences.getString("value"+i, ""));
//
//
//        }else{
//            holder.count.setText(String.valueOf(quantity));
//           // titleTextView.setText(String.valueOf(1));
//        }


//
//
//
//        if(sharedpreferences.contains("sum")) {
//            Sum = Float.parseFloat(sharedpreferences.getString("sum", ""));
//            Intent intent = new Intent("custom-message");
//            intent.putExtra("sum", String.valueOf(Sum));
//            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//
//        }else{
////               for (int u = 0; u < names.size(); u++) {
////                 //  Sum += Float.parseFloat(holder.total.getText().toString());
////                   Intent intent = new Intent("custom-message");
////                   intent.putExtra("sum", String.valueOf(Sum));
////                   LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
////
////               }
//        }

        holder.textView.setText(names.get(i));
        holder.Cprice.setText(price.get(i));
        holder. Cprice.setPaintFlags(holder.Cprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.Csale.setText(sale.get(i));
        holder. Csale.setPaintFlags(holder.Csale.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        final productAdapter.ViewHolder finalHolder = holder;
        quantity=Integer.parseInt(titleTextView.getText().toString());
        tota = Float.parseFloat(titleTextView.getText().toString()) * Float.parseFloat(price.get(i));
        holder.total.setText(String.valueOf(tota));
//        if(Pstock.get(i).equals("0")){
//           // holder.sold.setVisibility(View.VISIBLE);
//            holder.cart.setVisibility(View.INVISIBLE);
//            holder.notify.setVisibility(View.VISIBLE);
//
//        }

        final productAdapter.ViewHolder finalHolder1 = holder;
        final productAdapter.ViewHolder finalHolder3 = holder;
        userDbHelper=new UserDbHelper(context);
        sqLiteDatabase=userDbHelper.getWritableDatabase();

        final ViewHolder finalHolder2 = holder;
        final ViewHolder finalHolder5 = holder;
        holder.notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "We Will Notify You ....", Toast.LENGTH_SHORT).show();
            }
        });

        final ViewHolder finalHolder7 = holder;
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Pstock.get(i).equals("0")) {
//                    // holder.sold.setVisibility(View.VISIBLE);
//                    finalHolder7.cart.setVisibility(View.INVISIBLE);
//                    finalHolder7.notify.setVisibility(View.VISIBLE);
//
//                } else {
                    finalHolder2.linearLayout.setVisibility(View.VISIBLE);
                    finalHolder5.cart.setVisibility(View.INVISIBLE);
                    String nn = titleTextView.getText().toString();
                    Pcount.add(i, nn);
                    tota = Float.parseFloat(titleTextView.getText().toString()) * Float.parseFloat(price.get(i));
                    finalHolder1.total.setText(String.valueOf(tota));
                    String t = finalHolder1.total.getText().toString();
                    Psubtotal.add(i, t);

                    String idd = id.get(i);
                    String na = names.get(i);
                    String pr = price.get(i);
                    String sa = sale.get(i);
                    String stok = Pstock.get(i);
                    String it_c = Pcount.get(i);
                    String it_s = Psubtotal.get(i);
                    String imge = imgurl.get(i);
                    // Log.e("lo",Pcount.toString());


                    Cursor c = sqLiteDatabase.rawQuery("SELECT *    FROM " + UserData.NewUserData.TABLE_NAME
                                    + " WHERE " + UserData.NewUserData.product_name +
                                    " = " + "'" + na + "'"
                            , null);

                    if (c.moveToFirst()) {
                        boolean update = userDbHelper.updateInfo(idd, na, imge, pr, sa, stok, it_c, it_s);

                        if (update == true) {
                            // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                            Toast.makeText(context, "Added To Cart  ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        boolean insert = userDbHelper.AddInfo(idd, na, imge, pr, sa, stok, it_c, it_s);

                        if (insert == true) {
                            // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                            // Toast.makeText(context, "One Row Saved", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                        }


                    }
                    userDbHelper.close();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("value" + i, nn);
                    editor.putString("sum", String.valueOf(Sum));
                    editor.apply();
                    Log.e("df" + (i), sharedpreferences.getString("value" + i, ""));


                }
           // }
        });


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
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("value"+i, nn);
                    Log.e("count",Pcount.get(i).toString());
                    editor.apply();
                    Psubtotal.add(i,t);
                    Pcount.add(i,nn);
                    String idd=id.get(i);
                    String na=names.get(i);
                    String pr=price.get(i);
                    String sa=sale.get(i);
                    String stok=Pstock.get(i);
                    String it_c=Pcount.get(i);
                    String it_s=Psubtotal.get(i);
                    String imge=imgurl.get(i);
                    Log.e("lo",Pcount.get(i).toString());


                    Cursor c=  sqLiteDatabase.rawQuery("SELECT *    FROM "+ UserData.NewUserData.TABLE_NAME
                                    + " WHERE " + UserData.NewUserData.product_name +
                                    " = " + "'" + na + "'"
                            , null);

                    if(c.moveToFirst())
                    {
                        boolean update =userDbHelper.updateInfo(idd,na,imge,pr,sa,stok,it_c,it_s);

                        if(update==true) {

                            Toast.makeText(context, "Added To Cart  ", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                        }



                    }
                    userDbHelper.close();



                    Log.e("df"+(i),sharedpreferences.getString("value"+i,""));


                }

            }
        });

        final productAdapter.ViewHolder finalHolder4 = holder;
        final ViewHolder finalHolder6 = holder;
        holder.Cdecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

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
                                    finalHolder6.linearLayout.setVisibility(View.INVISIBLE);
                                    finalHolder6.cart.setVisibility(View.VISIBLE);
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




                }else {
                    quantity--;
                    titleTextView.setText(String.valueOf(quantity));
                    String n = titleTextView.getText().toString();
                    Pcount.add(i,n);
                    Log.e("count",Pcount.get(i).toString());

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
                    Cursor c=  sqLiteDatabase.rawQuery("SELECT *    FROM "+ UserData.NewUserData.TABLE_NAME
                                    + " WHERE " + UserData.NewUserData.product_name +
                                    " = " + "'" + na + "'"
                            , null);

                    if(c.moveToFirst())
                    {
                        boolean update =userDbHelper.updateInfo(idd,na,imge,pr,sa,stok,it_c,it_s);

                        if(update==true) {

                            Toast.makeText(context, "Added To Cart  ", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                        }

                    }else {
                        boolean insert =userDbHelper.AddInfo(idd,na,imge,pr,sa,stok,it_c,it_s);

                        if(insert==true) {
                            // Toast.makeText(getBaseContext(), item+""+count+"", Toast.LENGTH_LONG).show();
                            // Toast.makeText(context, "One Row Saved", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                        }

                    }

                    userDbHelper.close();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("value"+i, String.valueOf(quantity));
                    editor.putString("sum"+i,String.valueOf(Sum));
                    editor.apply();
                }
            }
        });


        Picasso.with(context)
                .load(imgurl.get(i))
                .noFade().resize(holder.CIMG.getWidth(), 200)

                .into(holder.CIMG);










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

//    private void setScaleAnimation(View view) {
//        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        anim.setDuration(1000);
//        view.startAnimation(anim);
//    }
//
//    @Override
//    public void onViewDetachedFromWindow(productAdapter.ViewHolder holder) {
//        clearAnimation(holder);
//    }
//    public void clearAnimation(productAdapter.ViewHolder holder)
//    {
//        holder.soldoutIMG.clearAnimation();
//    }
}
