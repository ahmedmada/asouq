package com.example.hp.aswaq;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;

public class Favourit extends AppCompatActivity
{
    ListView FavList;
    ArrayList<String> F_ImageUrl;
    ArrayList<String>F_item_id;
    ArrayList<String>F_item_name;
    ArrayList<String>F_price;
    ArrayList<String>F_sale;

    UserDbHelperFav userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    favourit_adapter favourit_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourit);
        FavList=findViewById(R.id.favList);
        F_item_name=new ArrayList<>();
        F_ImageUrl=new ArrayList<>();
        F_price=new ArrayList<>();
        F_sale=new ArrayList<>();


        userDbHelper=new UserDbHelperFav(getApplicationContext());

        cursor=userDbHelper.GetData(sqLiteDatabase);
        if(cursor.moveToFirst())
        {
            do {
                String nam, img,pric,sal;
                nam = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.Fav_name));
                img = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.Fav_img));
                pric = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.Fav_price));
                sal = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.Fav_sale));


                F_item_name.add(nam);
                F_sale.add(sal);
                F_price.add(pric);
                F_ImageUrl.add(img);

            } while (cursor.moveToNext());
        }
        favourit_adapter=new favourit_adapter(this,F_ImageUrl,F_item_name,F_price,F_sale);
        FavList.setAdapter(favourit_adapter);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Favourit.this,Cart.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
