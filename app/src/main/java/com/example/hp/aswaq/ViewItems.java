package com.example.hp.aswaq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewItems extends AppCompatActivity implements View.OnClickListener
{
   // ListView itemList;
    GridView productgrid;

    String t_id;
    ItemsModel itemsModel=new ItemsModel();
    ArrayList<String> ImageUrl;
    ArrayList<String> item_id;
    ArrayList<String>item_name;
    ArrayList<String>price;
    ArrayList<String>stock;
    ArrayList<String>sale;

    ArrayList<String>coun;
  //  itemsListviewAdapter adapter;
    productAdapter adapter;
    Button AddToCart;
    ImageView Fav;

    SharedPreferences sharedpreferences;
    boolean flag=false;
    String main_slug,sub_slug;

    UserDbHelper userDbHelper;

    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
       // itemList=findViewById(R.id.itemslist);
        productgrid=findViewById(R.id.griditems);

        userDbHelper=new UserDbHelper(getApplicationContext());
        String totalCartPrice = userDbHelper.getTotalCartPrice();




        Intent intent = getIntent();
        t_id = intent.getStringExtra(Conf.term_taxonomy_id);

        main_slug=intent.getStringExtra("PMS");
        sub_slug=intent.getStringExtra("PSS");



        ImageUrl=new ArrayList<>();
        item_name=new ArrayList<>();
        item_id=new ArrayList<>();
        price=new ArrayList<>();
        stock=new ArrayList<>();
        sale=new ArrayList<>();
        coun=new ArrayList<>();
//        userDbHelper=new UserDbHelper(getApplicationContext());
//
//        cursor=userDbHelper.GetData(sqLiteDatabase);
//        if(cursor.moveToFirst())
//        {
//            do {
//                String nam, img,pric,sal,id,stock,count,subtotal;
//                id = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_id));
//                nam = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_name));
//                img = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_img));
//                pric = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_price));
//                sal = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_sale));
//                stock = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_stock));
//                count = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_count));
//                subtotal = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_subtotal));
//
//
//                coun.add(count);
//
//
//
//            } while (cursor.moveToNext());
//        }else{
//
//            for(int x=0;x<item_name.size();x++){
//                coun.add(x,String.valueOf(0));
//
//            }
//        }
//      itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                pos=i;
//            }
//        });

        GetItems();
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
            startActivity(new Intent(ViewItems.this,Cart.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void GetItems()
    {
        // AndroidNetworking.get()
        class GetSub extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewItems.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                ShowItems(s);
//
               // adapter=new itemsListviewAdapter(getApplicationContext(),ImageUrl,item_name,price,sale,item_id,stock);
              adapter=  new productAdapter(ViewItems.this,ImageUrl,item_name,price,sale,item_id,stock);
               // itemList.setAdapter(adapter);
                productgrid.setAdapter(adapter);
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Conf.items_url,t_id);
                return s;

            }
        }
        GetSub gr = new GetSub();
        gr.execute();
    }
    public void ShowItems(String json)
    {
        item_name = new ArrayList<>();
        item_id = new ArrayList<>();
        ImageUrl = new ArrayList<>();
        price = new ArrayList<>();
        sale = new ArrayList<>();
        stock = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray items = jsonObject.getJSONArray(Conf.items_arr);

            for(int i = 0; i<items.length(); i++)
            {
                JSONObject object = items.getJSONObject(i);
                item_name.add(object.getString(Conf.post_title));
                item_id.add(object.getString(Conf.ID));
                ImageUrl.add(object.getString(Conf.items_guid));
            }

            JSONArray meta=jsonObject.getJSONArray(Conf.items_meta);
            for(int f=0;f<meta.length();f++)
            {
                JSONObject metaobj=meta.getJSONObject(f);
                String metakey=metaobj.getString(Conf.meta_key);
                String metavalue=metaobj.getString(Conf.items_meta_value);

                if(metakey.equals("_regular_price")){

                    price.add(metavalue);
                }

                if(metakey.equals("_sale_price")){

                    sale.add(metavalue);
                }
                if(metakey.equals("_stock")){
                    stock.add(metavalue);
                }

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addTocart){
          //  Toast.makeText(this, "pod"+pos, Toast.LENGTH_SHORT).show();

        }

    }

}


//    @Override
//    public void onClick(View view) {
//
//        if (view.getId()==R.id.addTocart){
//            View parentRow = (View) view.getParent();
//            ListView listView = (ListView) parentRow.getParent();
//            final int position = listView.getPositionForView(parentRow);
//            Log.e("hh", String.valueOf(position));
//
//        }
//
////
////        ImageView  Fav=view.findViewById(R.id.favImg);
////        switch (view.getId()){
////            case R.id.addTocart:
////                Toast.makeText(ViewItems.this, "kkkkkkkkkkkkkkkkkkkkk", Toast.LENGTH_SHORT).show();
////                int pos =itemList.getSelectedItemPosition();
////                HashMap<String,String> data=new HashMap<>();
////                data.put("name",item_name.get(pos));
////                data.put("price",price.get(pos));
////                data.put("sale",sale.get(pos));
////                data.put("imgurl",ImageUrl.get(pos));
////
////                Intent dat=new Intent(ViewItems.this,Cart.class);
////                dat.putExtra("hash",dat);
////                startActivity(dat);
////                break;
////
////
////
////        }
//
//
//
//
//
//    }
//}
