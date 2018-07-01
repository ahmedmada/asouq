package com.example.hp.aswaq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity
{
    ListView cartListview;
    ArrayList<String> C_ImageUrl;
    ArrayList<String>C_item_id;
    ArrayList<String>C_item_name;
    ArrayList<String>C_price;
    ArrayList<String>C_sale;
    ArrayList<String>C_stock;
    ArrayList<String>C_count;
    ArrayList<String>C_subtotal;
    HashMap<String, String> hashmap;
    Cart_adapter cart_adapter;

    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Button folow;
    TextView Total;


    List<CatModel> Catproduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartListview=findViewById(R.id.cartlist);
        folow=findViewById(R.id.foloww);
        Total=findViewById(R.id.total);

        C_item_name=new ArrayList<>();
        C_ImageUrl=new ArrayList<>();
        C_price=new ArrayList<>();
        C_sale=new ArrayList<>();
        C_stock=new ArrayList<>();
        C_item_id=new ArrayList<>();
        C_count=new ArrayList<>();
        C_subtotal=new ArrayList<>();

//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("custom-message"));


        userDbHelper=new UserDbHelper(getApplicationContext());

        cursor=userDbHelper.GetData(sqLiteDatabase);
        if(cursor.moveToFirst())
        {
            do {
                String nam, img,pric,sal,id,stock,count,subtotal;
                id = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_id));
                nam = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_name));
                img = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_img));
                pric = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_price));
                sal = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_sale));
                stock = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_stock));
                count = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_count));
                subtotal = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_subtotal));

                CatModel dataModel=new CatModel();

                dataModel.setname(nam);
                dataModel.setImageUrl(img);
                dataModel.setPrice(pric);
                dataModel.setSale(sal);
                Catproduct.add(dataModel);
                C_item_name.add(nam);
            C_sale.add(sal);
            C_price.add(pric);
            C_ImageUrl.add(img);
            C_item_id.add(id);
            C_stock.add(stock);
            C_count.add(count);
            C_subtotal.add(subtotal);


            } while (cursor.moveToNext());
        }
        cart_adapter=new Cart_adapter(this,C_ImageUrl,C_item_name,C_price,C_sale,C_item_id,C_stock,C_count,C_subtotal);
            cartListview.setAdapter(cart_adapter);
        String totalCartPrice = userDbHelper.getTotalCartPrice();
        if(totalCartPrice==null)
        {
            Total.setText("المجموع : 0.0");

        } else{

            Total.setText("المجموع : "+totalCartPrice);
            }


            folow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(Cart.this,C_item_name+C_item_id.toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Cart.this,FolowingProducts.class));
//                    Boolean Registered;
//                    final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    Registered = sharedPref.getBoolean("Registered", false);
//
//                    if (!Registered)
//                    {
//                        startActivity(new Intent(Cart.this,Regisetration.class));
//                    }else {
//                        startActivity(new Intent(Cart.this,FolowingProducts.class));
//                    }
                }
            });







    }

}
