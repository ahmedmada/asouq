package com.example.hp.aswaq;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.hp.aswaq.Interface.InterfaceRetro;
import com.example.hp.aswaq.Order.Billing;
import com.example.hp.aswaq.Order.LineItem;
import com.example.hp.aswaq.Order.Order;
import com.example.hp.aswaq.Order.Shipping;
import com.example.hp.aswaq.RetriveOrder.RetriveOrder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class FolowingProducts extends AppCompatActivity

{

    EditText Fname,Lname,ST,ST2,Tel,Mail;
    Spinner City,Gov;

    TextView hello;
    Button ok;
    int id=3;
    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    ArrayList<String> C_ImageUrl;
    ArrayList<String>C_item_id;
    ArrayList<String>C_item_name;
    ArrayList<String>C_price;
    ArrayList<String>C_sale;
    ArrayList<String>C_count;
    ArrayList<String>C_subtotal;
    ArrayList<String>C_stock;

    private static final String[]cityies = {" أختر مدينه", " طنطا"};
    private static final String[]goves = {" اختر محافظه ", "الغربيه "};

    String spinner_city;
    String spinner_gov;

    List<LineItem> lineItems;
    LineItem lineItem;
    Billing billing = new Billing();
    Shipping shipping = new Shipping();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folowing_products);



        Fname=findViewById(R.id.Fname);
        Lname=findViewById(R.id.Lname);
        ST=findViewById(R.id.St);
        ST2=findViewById(R.id.St2);
        City=findViewById(R.id.city);
        Gov=findViewById(R.id.gov);
        Tel=findViewById(R.id.tel);
        Mail=findViewById(R.id.mail);
        ok=findViewById(R.id.ok);
        hello=findViewById(R.id.helo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FolowingProducts.this,
                android.R.layout.simple_spinner_item,cityies);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        City.setAdapter(adapter);

        ArrayAdapter<String>adapter_gov = new ArrayAdapter<String>(FolowingProducts.this,
                android.R.layout.simple_spinner_item,goves);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gov.setAdapter(adapter_gov);

        Gov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_gov=Gov.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_city=City.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        AndroidNetworking.initialize(getApplicationContext());
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveChanges();
                testSendOrder();
                // Build();
                //  Toast.makeText(FolowingProducts.this, "شكرا لاستخدامك أسواق الشريف وستم التوصيل ف أسرع وقت",
                //  Toast.LENGTH_LONG).show();
                //  startActivity(new Intent(FolowingProducts.this,MainActivity.class));
            }
        });
        C_item_name=new ArrayList<>();
        C_ImageUrl=new ArrayList<>();
        C_price=new ArrayList<>();
        C_sale=new ArrayList<>();
        C_stock=new ArrayList<>();
        C_item_id=new ArrayList<>();
        C_count=new ArrayList<>();
        C_subtotal=new ArrayList<>();
        userDbHelper=new UserDbHelper(getApplicationContext());

        cursor=userDbHelper.GetData(sqLiteDatabase);
        if(cursor.moveToFirst())
        {
            lineItems = new ArrayList<>();
            do {
                lineItem = new LineItem();

                String nam, img,pric,sal,id,stock,qty,stot;
                id = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_id));
                nam = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_name));
                img = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_img));
                pric = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_price));
                sal = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_sale));
                stock = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_stock));
                qty = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_count));
                stot = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_subtotal));

                lineItem.setProductId(Long.valueOf(id));
                lineItem.setQuantity(Long.valueOf(qty));

                lineItems.add(lineItem);
                CatModel dataModel=new CatModel();


                dataModel.setname(nam);
                dataModel.setImageUrl(img);
                dataModel.setPrice(pric);
                dataModel.setSale(sal);
                //Catproduct.add(dataModel);
                C_item_name.add(nam);
                C_sale.add(sal);
                C_price.add(pric);
                C_ImageUrl.add(img);
                C_item_id.add(id);
                C_stock.add(stock);
                C_count.add(qty);
                C_subtotal.add(stot);




            } while (cursor.moveToNext());
        }
        Intent in=getIntent();


        Boolean Registered;
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences  sharedpreferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
        Registered = sharedPref.getBoolean("Registered", false);
        if (sharedpreferences.contains("mail"))
        {


            hello.setText(sharedpreferences.getString("mail", ""));

        }else{


        }


//

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.log) {


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FolowingProducts.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(FolowingProducts.this,Cart.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    public void getSqlite(){

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
                Log.e("lo",count);

                dataModel.setname(nam);
                dataModel.setImageUrl(img);
                dataModel.setPrice(pric);
                dataModel.setSale(sal);
               // Catproduct.add(dataModel);
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




    }*/

    public void saveChanges()
    {
        final String RFname = Fname.getText().toString().trim();
        final String RLname = Lname.getText().toString().trim();
        final String RST = ST.getText().toString().trim();
        final String RST2 = ST2.getText().toString().trim();
        final String RCity = spinner_city.trim();
        final String RGov=spinner_gov.trim();


        final String RTel=Tel.getText().toString().trim();
        final String RMail=Mail.getText().toString().trim();
        final String id="1";

        billing.setFirstName(RFname);
        billing.setLastName(RLname);
        billing.setAddress1(RCity);
        billing.setAddress2(RGov);
        billing.setPhone(RTel);
        billing.setEmail(RMail);

        shipping.setFirstName(RFname);
        shipping.setLastName(RLname);
        shipping.setAddress1(RST);
        shipping.setAddress2(RST2);
        shipping.setCity(RCity);
        shipping.setCountry("Egypt");

        JsonArray JName=new JsonArray();
        JsonArray Jprice=new JsonArray();
        JsonArray Jsale=new JsonArray();
        JsonArray Jid=new JsonArray();
        JsonArray Jimage=new JsonArray();
        JsonArray Jstock=new JsonArray();
        JsonArray Jcount=new JsonArray();
        JsonArray Jsubtotal=new JsonArray();

        for(int s=0;s<C_item_name.size();s++){
            JName.add(C_item_name.get(s));

        }
        for(int s=0;s<C_price.size();s++){
            Jprice.add(C_price.get(s));

        }
        for(int s=0;s<C_sale.size();s++){
            Jsale.add(C_sale.get(s));

        }
        for(int s=0;s<C_stock.size();s++){
            Jstock.add(C_stock.get(s));

        }
        for(int s=0;s<C_item_id.size();s++){
            Jid.add(C_item_id.get(s));

        }
        for(int s=0;s<C_ImageUrl.size();s++){
            Jimage.add(C_ImageUrl.get(s));

        }
        for(int s=0;s<C_item_id.size();s++){
            Jcount.add(C_count.get(s));

        }
        for(int s=0;s<C_item_id.size();s++){
            Jsubtotal.add(C_subtotal.get(s));

        }
        final String namelist=JName.toString();
        final String priceList=Jprice.toString();
        final String saleList=Jsale.toString();
        final String stockList=Jstock.toString();
        final String idList=Jid.toString();
        final String imgeList=Jimage.toString();
        final String countlist=Jcount.toString();
        final String subtotaList=Jsubtotal.toString();


        class saveChang extends AsyncTask<Void,Void,String>
        {

            ProgressDialog loading;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(FolowingProducts.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);

                loading.dismiss();
                if(s.equals("123b:0;4")){

                    Build();
                }else{

                    Log.e("g",s);
                }
            }

            @Override
            protected String doInBackground(Void... v) {


                HashMap<String,String> paramss = new HashMap<>();
                paramss.put(Conf.RFname, RFname);
                paramss.put("id",id);
                paramss.put(Conf.RLname, RLname);
                paramss.put(Conf.RST,RST);


                paramss.put(Conf.RCity, RCity);
                paramss.put(Conf.RGov, RGov);
                paramss.put(Conf.RTel, RTel);
                paramss.put(Conf.RMail, RMail);
                paramss.put(Conf.Sname,namelist);
                paramss.put(Conf.Sid,idList);
                paramss.put(Conf.Simage,imgeList);
                paramss.put(Conf.Sprice,priceList);
                paramss.put(Conf.Ssale,saleList);
                paramss.put(Conf.Sstock,stockList);
                paramss.put(Conf.Scount,countlist);
                paramss.put(Conf.Ssubtota,subtotaList);


                RequestHandler Rh = new RequestHandler();
                String res = Rh.sendPostRequest(Conf.session_url, paramss);
                Log.e("list",paramss.toString());
                return res;
            }
        }

        saveChang sr = new saveChang();
        sr.execute();


    }
    public void SaveRecipt()
    {
        final String RFname = String.valueOf(Fname.getText()).trim();
        final String RLname = String.valueOf(Lname.getText()).trim();
        final String RST = String.valueOf(ST.getText()).trim();
        final String RCity = spinner_city.trim();
        final String RGov=spinner_gov.trim();

        final String RTel=String.valueOf(Tel.getText()).trim();
        final String RMail=String.valueOf(Mail.getText()).trim();

        final  String Id=String.valueOf(id).trim();



        Log.e("result",(RFname+RLname+RST+RCity+RGov+RMail));
        AndroidNetworking.post(Conf.session_url)
                .addBodyParameter(Conf.RFname, RFname)
                .addBodyParameter("id", Id)
                .addBodyParameter(Conf.RLname, RLname)
                .addBodyParameter(Conf.RST,RST)
                .addBodyParameter(Conf.RCity, RCity)
                .addBodyParameter(Conf.RGov, RGov)
                .addBodyParameter(Conf.RTel, RTel)
                .addBodyParameter(Conf.RMail, RMail)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if(response.equalsIgnoreCase("123b:0;4")){
                            Toast.makeText(FolowingProducts.this, "Your Result Recorded", Toast.LENGTH_SHORT).show();
                            Build();

                        }
                        else if(response.equalsIgnoreCase("nooo")){

                            Log.e("res",response);
                        }
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        Log.e("err",anError.toString());


                    }
                });


    }
    public void Build()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
        alertDialog.setTitle(" أسواق الشريف");
        alertDialog.setMessage("شكرا لاستخدامكم أسواق الشريف وسيتم التوصيل ف أسرع وقت  ");


        alertDialog.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog.cancel();
                        startActivity(new Intent(FolowingProducts.this,MainActivity.class));
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

    public void testSendOrder(){

        Log.v("aaaaaaaaaaaaaaaaaa",   "1");

        final List<RetriveOrder> retriveOrders =new ArrayList<>();
        Order order =new Order();
        order.setPaymentMethod("الدفع نقدًا عند الإستلام");
        order.setBilling(billing);
        order.setShipping(shipping);
        order.setLineItems(lineItems);
        order.setSetPaid(false);
        Log.v("aaaaaaaaaaaaaaaaaa",   "2");


//        String url = "http://fictionapps.com/demo/souq/wp-json/wc/v2/";
        String url = "http://mo-mart.com/wp-json/wc/v2/";

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer("ck_c1d1b2f48c0dcd6a7b2af63b37bf07a1056cc4e0",
                "cs_138bcc416ae4442f81031320897b58791b94f456");
//        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer("ck_baeba18c4bf961af446ad4aad1aaf50b46b16e95",
//                "cs_10467bc00edd399bcd24e21f7f3c338c02975dd0");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        InterfaceRetro service = retrofit.create(InterfaceRetro.class);

        service.creatOrder(order).enqueue(new Callback<List<RetriveOrder>>() {
            @Override
            public void onResponse(@NonNull Call<List<RetriveOrder>> call, @NonNull Response<List<RetriveOrder>> response) {
                Log.v("aaaaaaaaaaaaaaaaaa",   "done");

                if (response.body() != null) {
                    retriveOrders.addAll(response.body());
                    Log.v("aaaaaaaaaaaaaaaaaa", retriveOrders.size()+  " >> size");

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RetriveOrder>> call, @NonNull Throwable t) {
                Log.v("Error", t.getMessage() + "");
                Log.v("aaaaaaaaaaaaaaaaaa", t.getMessage() + "");

            }
        });

    }

}
/*
package com.example.hp.aswaq;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.hp.aswaq.Interface.InterfaceRetro;
import com.example.hp.aswaq.Order.Billing;
import com.example.hp.aswaq.Order.LineItem;
import com.example.hp.aswaq.Order.Order;
import com.example.hp.aswaq.RetriveOrder.RetriveOrder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class FolowingProducts extends AppCompatActivity

{

    EditText Fname,Lname,ST,Tel,Mail;
    Spinner City,Gov;
    TextView hello;
    Button ok;
    int id=3;
    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    private static final String[]cityies = {" أختر مدينه", " طنطا"};
    private static final String[]goves = {" اختر محافظه ", "الغربيه "};

    ArrayList<String> C_ImageUrl;
    ArrayList<String>C_item_id;
    ArrayList<String>C_item_name;
    ArrayList<String>C_price;
    ArrayList<String>C_sale;
    ArrayList<String>C_count;
    ArrayList<String>C_subtotal;
    ArrayList<String>C_stock;

    String spinner_city;
    String spinner_gov;


    List<LineItem> lineItems;
    LineItem lineItem;
    Billing billing = new Billing();
    double totalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folowing_products);


        Fname=findViewById(R.id.Fname);
        Lname=findViewById(R.id.Lname);
        ST=findViewById(R.id.St);
        City=findViewById(R.id.city);
        Gov=findViewById(R.id.gov);
        Tel=findViewById(R.id.tel);
        Mail=findViewById(R.id.mail);
        ok=findViewById(R.id.ok);
        hello=findViewById(R.id.helo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FolowingProducts.this,
                android.R.layout.simple_spinner_item,cityies);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        City.setAdapter(adapter);

        ArrayAdapter<String>adapter_gov = new ArrayAdapter<String>(FolowingProducts.this,
                android.R.layout.simple_spinner_item,goves);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gov.setAdapter(adapter_gov);

     Gov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             spinner_gov=Gov.getItemAtPosition(i).toString();
         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
     });
     City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             spinner_city=City.getItemAtPosition(i).toString();
         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
     });
        AndroidNetworking.initialize(getApplicationContext());
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveChanges();
                testSendOrder();
                // Build();
                //  Toast.makeText(FolowingProducts.this, "شكرا لاستخدامك أسواق الشريف وستم التوصيل ف أسرع وقت",
                //  Toast.LENGTH_LONG).show();
                //  startActivity(new Intent(FolowingProducts.this,MainActivity.class));
            }
        });
        C_item_name=new ArrayList<>();
        C_ImageUrl=new ArrayList<>();
        C_price=new ArrayList<>();
        C_sale=new ArrayList<>();
        C_stock=new ArrayList<>();
        C_item_id=new ArrayList<>();
        C_count=new ArrayList<>();
        C_subtotal=new ArrayList<>();
        userDbHelper=new UserDbHelper(getApplicationContext());

        cursor=userDbHelper.GetData(sqLiteDatabase);
        if(cursor.moveToFirst())
        {
            lineItems = new ArrayList<>();
            do {
                lineItem = new LineItem();

                String nam, img,pric,sal,id,stock,qty,stot;
                id = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_id));
                nam = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_name));
                img = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_img));
                pric = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_price));
                sal = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_sale));
                stock = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_stock));
                qty = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_count));
                stot = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_subtotal));

                lineItem.setProductId(Long.valueOf(id));
                lineItem.setQuantity(Long.valueOf(qty));

                totalPrice += (Double.parseDouble(pric)*Double.parseDouble(qty));
                lineItems.add(lineItem);
                CatModel dataModel=new CatModel();


                dataModel.setname(nam);
                dataModel.setImageUrl(img);
                dataModel.setPrice(pric);
                dataModel.setSale(sal);
                //Catproduct.add(dataModel);
                C_item_name.add(nam);
                C_sale.add(sal);
                C_price.add(pric);
                C_ImageUrl.add(img);
                C_item_id.add(id);
                C_stock.add(stock);
                C_count.add(qty);
                C_subtotal.add(stot);




            } while (cursor.moveToNext());
        }
        Intent in=getIntent();


        Boolean Registered;
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences  sharedpreferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
        Registered = sharedPref.getBoolean("Registered", false);
        if (sharedpreferences.contains("mail"))
        {


            hello.setText(sharedpreferences.getString("mail", ""));

        }else{


        }


//

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.log) {


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FolowingProducts.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(FolowingProducts.this,Cart.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void saveChanges()
    {
        final String RFname = Fname.getText().toString().trim();
        final String RLname = Lname.getText().toString().trim();
        final String RST = ST.getText().toString().trim();
        final String RCity = spinner_city.trim();
        final String RGov=spinner_gov.trim();
        final String RTel=Tel.getText().toString().trim();
        final String RMail=Mail.getText().toString().trim();
        final String id="1";
        Log.e("tan",RCity);

        billing.setFirstName(RFname);
        billing.setLastName(RLname);
        billing.setAddress1(RCity);
        billing.setAddress2(RGov);
        billing.setPhone(RTel);
        billing.setEmail(RMail);


        JsonArray JName=new JsonArray();
        JsonArray Jprice=new JsonArray();
        JsonArray Jsale=new JsonArray();
        JsonArray Jid=new JsonArray();
        JsonArray Jimage=new JsonArray();
        JsonArray Jstock=new JsonArray();
        JsonArray Jcount=new JsonArray();
        JsonArray Jsubtotal=new JsonArray();

        for(int s=0;s<C_item_name.size();s++){
            JName.add(C_item_name.get(s));

        }
        for(int s=0;s<C_price.size();s++){
            Jprice.add(C_price.get(s));

        }
        for(int s=0;s<C_sale.size();s++){
            Jsale.add(C_sale.get(s));

        }
        for(int s=0;s<C_stock.size();s++){
            Jstock.add(C_stock.get(s));

        }
        for(int s=0;s<C_item_id.size();s++){
            Jid.add(C_item_id.get(s));

        }
        for(int s=0;s<C_ImageUrl.size();s++){
            Jimage.add(C_ImageUrl.get(s));

        }
        for(int s=0;s<C_item_id.size();s++){
            Jcount.add(C_count.get(s));

        }
        for(int s=0;s<C_item_id.size();s++){
            Jsubtotal.add(C_subtotal.get(s));

        }
        final String namelist=JName.toString();
        final String priceList=Jprice.toString();
        final String saleList=Jsale.toString();
        final String stockList=Jstock.toString();
        final String idList=Jid.toString();
        final String imgeList=Jimage.toString();
        final String countlist=Jcount.toString();
        final String subtotaList=Jsubtotal.toString();


        class saveChang extends AsyncTask<Void,Void,String>
        {

            ProgressDialog loading;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(FolowingProducts.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);

                loading.dismiss();
                if(s.equals("123b:0;4")){

                    Build();
                }else{

                    Log.e("g",s);
                }
            }

            @Override
            protected String doInBackground(Void... v) {


                HashMap<String,String> paramss = new HashMap<>();
                paramss.put(Conf.RFname, RFname);
                paramss.put("id",id);
                paramss.put(Conf.RLname, RLname);
                paramss.put(Conf.RST,RST);


                paramss.put(Conf.RCity, RCity);
                paramss.put(Conf.RGov, RGov);
                paramss.put(Conf.RTel, RTel);
                paramss.put(Conf.RMail, RMail);
                paramss.put(Conf.Sname,namelist);
                paramss.put(Conf.Sid,idList);
                paramss.put(Conf.Simage,imgeList);
                paramss.put(Conf.Sprice,priceList);
                paramss.put(Conf.Ssale,saleList);
                paramss.put(Conf.Sstock,stockList);
                paramss.put(Conf.Scount,countlist);
                paramss.put(Conf.Ssubtota,subtotaList);


                RequestHandler Rh = new RequestHandler();
                String res = Rh.sendPostRequest(Conf.session_url, paramss);
                Log.e("list",paramss.toString());
                return res;
            }
        }

        saveChang sr = new saveChang();
        sr.execute();


    }

    public void Build()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
        alertDialog.setTitle(" أسواق الشريف");
        alertDialog.setMessage("شكرا لاستخدامكم أسواق الشريف وسيتم التوصيل ف أسرع وقت  ");


        alertDialog.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog.cancel();
                        startActivity(new Intent(FolowingProducts.this,MainActivity.class));
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

    public void testSendOrder(){

        Log.v("aaaaaaaaaaaaaaaaaa",   "1");

        final List<RetriveOrder> retriveOrders =new ArrayList<>();
        Order order =new Order();
        order.setPaymentMethod("الدفع عند التسليم");
        order.setBilling(billing);
        order.setLineItems(lineItems);
        order.setSetPaid(false);
        Log.v("aaaaaaaaaaaaaaaaaa",   "2");


        String url = "http://fictionapps.com/demo/souq/wp-json/wc/v2/";

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer("ck_baeba18c4bf961af446ad4aad1aaf50b46b16e95",
                "cs_10467bc00edd399bcd24e21f7f3c338c02975dd0");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        InterfaceRetro service = retrofit.create(InterfaceRetro.class);

        service.creatOrder(order).enqueue(new Callback<List<RetriveOrder>>() {
            @Override
            public void onResponse(@NonNull Call<List<RetriveOrder>> call, @NonNull Response<List<RetriveOrder>> response) {
                Log.v("aaaaaaaaaaaaaaaaaa",   "done");
                Log.e("bil", billing.toString());
                Log.v("line",  lineItems.toString());


                if (response.body() != null) {
                    retriveOrders.addAll(response.body());
                    Log.v("aaaaaaaaaaaaaaaaaa", retriveOrders.size()+  " >> size");

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RetriveOrder>> call, @NonNull Throwable t) {
                Log.v("Error", t.getMessage() + "");
                Log.v("aaaaaaaaaaaaaaaaaa", t.getMessage() + "");

            }
        });

    }

}



*/


//package com.example.hp.aswaq;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.AsyncTask;
//import android.preference.PreferenceManager;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Editable;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.Button;
//import android.view.View;
//import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.common.Priority;
//import com.androidnetworking.error.ANError;
//import com.androidnetworking.interfaces.StringRequestListener;
//import com.example.hp.aswaq.Interface.InterfaceRetro;
//import com.example.hp.aswaq.Order.Billing;
//import com.example.hp.aswaq.Order.LineItem;
//import com.example.hp.aswaq.Order.Order;
//import com.example.hp.aswaq.RetriveOrder.RetriveOrder;
//import com.google.gson.JsonArray;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
//import se.akerfeldt.okhttp.signpost.SigningInterceptor;
//
//public class FolowingProducts extends AppCompatActivity
//
//{
//
//    EditText Fname,Lname,ST,City,Gov,Tel,Mail;
//    TextView hello;
//    Button ok;
//    int id=3;
//    UserDbHelper userDbHelper;
//    SQLiteDatabase sqLiteDatabase;
//    Cursor cursor;
//    double totalPrice=0;
//
//    ArrayList<String> C_ImageUrl;
//    ArrayList<String>C_item_id;
//    ArrayList<String>C_item_name;
//    ArrayList<String>C_price;
//    ArrayList<String>C_sale;
//    ArrayList<String>C_count;
//    ArrayList<String>C_subtotal;
//    ArrayList<String>C_stock;
//    Billing billing = new Billing();
//    List<LineItem> lineItems;
//    LineItem lineItem;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_folowing_products);
//
//
//        Fname=findViewById(R.id.Fname);
//        Lname=findViewById(R.id.Lname);
//        ST=findViewById(R.id.St);
//        City=findViewById(R.id.city);
//        Gov=findViewById(R.id.gov);
//        Tel=findViewById(R.id.tel);
//        Mail=findViewById(R.id.mail);
//        ok=findViewById(R.id.ok);
//        hello=findViewById(R.id.helo);
//        AndroidNetworking.initialize(getApplicationContext());
//        ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                   saveChanges();
//                    testSendOrder();
//                   // Build();
//                  //  Toast.makeText(FolowingProducts.this, "شكرا لاستخدامك أسواق الشريف وستم التوصيل ف أسرع وقت",
//                          //  Toast.LENGTH_LONG).show();
//                  //  startActivity(new Intent(FolowingProducts.this,MainActivity.class));
//                }
//            });
//        C_item_name=new ArrayList<>();
//        C_ImageUrl=new ArrayList<>();
//        C_price=new ArrayList<>();
//        C_sale=new ArrayList<>();
//        C_stock=new ArrayList<>();
//        C_item_id=new ArrayList<>();
//        C_count=new ArrayList<>();
//        C_subtotal=new ArrayList<>();
//        userDbHelper=new UserDbHelper(getApplicationContext());
//
//        cursor=userDbHelper.GetData(sqLiteDatabase);
//        if(cursor.moveToFirst())
//        {
//            lineItems = new ArrayList<>();
//            do {
//                lineItem = new LineItem();
//                String nam, img,pric,sal,id,stock,qty,stot;
//                id = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_id));
//                nam = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_name));
//                img = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_img));
//                pric = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_price));
//                sal = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_sale));
//                stock = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_stock));
//                qty = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_count));
//                stot = cursor.getString(cursor.getColumnIndexOrThrow(UserData.NewUserData.product_subtotal));
//                lineItem.setProductId(Long.valueOf(id));
//                lineItem.setQuantity(Long.valueOf(qty));
//                totalPrice += (Double.parseDouble(pric)*Double.parseDouble(qty));
//
//                lineItems.add(lineItem);
//                CatModel dataModel=new CatModel();
//
//
//                dataModel.setname(nam);
//                dataModel.setImageUrl(img);
//                dataModel.setPrice(pric);
//                dataModel.setSale(sal);
//                //Catproduct.add(dataModel);
//                C_item_name.add(nam);
//                C_sale.add(sal);
//                C_price.add(pric);
//                C_ImageUrl.add(img);
//                C_item_id.add(id);
//                C_stock.add(stock);
//                C_count.add(qty);
//                C_subtotal.add(stot);
//
//
//
//            } while (cursor.moveToNext());
//        }
//        Intent in=getIntent();
//
//
//        Boolean Registered;
//        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences  sharedpreferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
//        Registered = sharedPref.getBoolean("Registered", false);
//        if (sharedpreferences.contains("mail"))
//        {
//
//
//                hello.setText(sharedpreferences.getString("mail", ""));
//
//        }else{
//
//
//        }
//
//
//
//
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.log) {
//
//
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FolowingProducts.this);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
//            editor.commit();
//            Intent i = new Intent(FolowingProducts.this,Cart.class);
//            startActivity(i);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//    public void getSqlite(){
//
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
//
//                CatModel dataModel=new CatModel();
//                Log.e("lo",count);
//
//                dataModel.setname(nam);
//                dataModel.setImageUrl(img);
//                dataModel.setPrice(pric);
//                dataModel.setSale(sal);
//               // Catproduct.add(dataModel);
//                C_item_name.add(nam);
//                C_sale.add(sal);
//                C_price.add(pric);
//                C_ImageUrl.add(img);
//                C_item_id.add(id);
//                C_stock.add(stock);
//                C_count.add(count);
//                C_subtotal.add(subtotal);
//
//
//            } while (cursor.moveToNext());
//        }
//
//
//
//
//    }
//
//    public void saveChanges()
//    {
//        final String RFname = Fname.getText().toString().trim();
//        final String RLname = Lname.getText().toString().trim();
//        final String RST = ST.getText().toString().trim();
//        final String RCity = City.getText().toString().trim();
//        final String RGov=Gov.getText().toString().trim();
//        final String RTel=Tel.getText().toString().trim();
//        final String RMail=Mail.getText().toString().trim();
//        final String id="3";
//
//        billing.setFirstName(RFname);
//        billing.setLastName(RLname);
//        billing.setAddress1(RCity);
//        billing.setAddress2(RGov);
//        billing.setPhone(RTel);
//        billing.setEmail(RMail);
//
//
//        JsonArray JName=new JsonArray();
//        JsonArray Jprice=new JsonArray();
//        JsonArray Jsale=new JsonArray();
//        JsonArray Jid=new JsonArray();
//        JsonArray Jimage=new JsonArray();
//        JsonArray Jstock=new JsonArray();
//        JsonArray Jcount=new JsonArray();
//        JsonArray Jsubtotal=new JsonArray();
//
//        for(int s=0;s<C_item_name.size();s++){
//            JName.add(C_item_name.get(s));
//
//        }
//        for(int s=0;s<C_price.size();s++){
//            Jprice.add(C_price.get(s));
//
//        }
//        for(int s=0;s<C_sale.size();s++){
//            Jsale.add(C_sale.get(s));
//
//        }
//        for(int s=0;s<C_stock.size();s++){
//            Jstock.add(C_stock.get(s));
//
//        }
//        for(int s=0;s<C_item_id.size();s++){
//            Jid.add(C_item_id.get(s));
//
//        }
//        for(int s=0;s<C_ImageUrl.size();s++){
//            Jimage.add(C_ImageUrl.get(s));
//
//        }
//        for(int s=0;s<C_item_id.size();s++){
//            Jcount.add(C_count.get(s));
//
//        }
//        for(int s=0;s<C_item_id.size();s++){
//            Jsubtotal.add(C_subtotal.get(s));
//
//        }
//        final String namelist=JName.toString();
//        final String priceList=Jprice.toString();
//        final String saleList=Jsale.toString();
//        final String stockList=Jstock.toString();
//        final String idList=Jid.toString();
//        final String imgeList=Jimage.toString();
//        final String countlist=Jcount.toString();
//        final String subtotaList=Jsubtotal.toString();
//
//
//        class saveChang extends AsyncTask<Void,Void,String>
//        {
//
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute()
//            {
//                super.onPreExecute();
//                loading = ProgressDialog.show(FolowingProducts.this,"Adding...","Wait...",false,false);
//            }
//
//            @Override
//            protected void onPostExecute(String s)
//            {
//                super.onPostExecute(s);
//
//                loading.dismiss();
//                if(s.equals("123b:0;4")){
//
//                    Build();
//                }else{
//
//                    Log.e("g",s);
//                }
//            }
//
//            @Override
//            protected String doInBackground(Void... v) {
//
//
//                HashMap<String,String> paramss = new HashMap<>();
//                paramss.put(Conf.RFname, RFname);
//                paramss.put("id",id);
//                paramss.put(Conf.RLname, RLname);
//                paramss.put(Conf.RST,RST);
//
//
//                paramss.put(Conf.RCity, RCity);
//                paramss.put(Conf.RGov, RGov);
//                paramss.put(Conf.RTel, RTel);
//                paramss.put(Conf.RMail, RMail);
//                paramss.put(Conf.Sname,namelist);
//                paramss.put(Conf.Sid,idList);
//                paramss.put(Conf.Simage,imgeList);
//                paramss.put(Conf.Sprice,priceList);
//                paramss.put(Conf.Ssale,saleList);
//                paramss.put(Conf.Sstock,stockList);
//                paramss.put(Conf.Scount,countlist);
//                paramss.put(Conf.Ssubtota,subtotaList);
//
//
//                RequestHandler Rh = new RequestHandler();
//                String res = Rh.sendPostRequest(Conf.session_url, paramss);
//                Log.e("list",paramss.toString());
//                return res;
//            }
//        }
//
//        saveChang sr = new saveChang();
//        sr.execute();
//
//
//    }
//    public void SaveRecipt()
//    {
//        final String RFname = String.valueOf(Fname.getText()).trim();
//        final String RLname = String.valueOf(Lname.getText()).trim();
//        final String RST = String.valueOf(ST.getText()).trim();
//        final String RCity = String.valueOf(City.getText()).trim();
//        final String RGov=String.valueOf(Gov.getText()).trim();
//        final String RTel=String.valueOf(Tel.getText()).trim();
//        final String RMail=String.valueOf(Mail.getText()).trim();
//
//        final  String Id=String.valueOf(id).trim();
//
//
//
//        Log.e("result",(RFname+RLname+RST+RCity+RGov+RMail));
//        AndroidNetworking.post(Conf.session_url)
//                .addBodyParameter(Conf.RFname, RFname)
//                .addBodyParameter("id", Id)
//                .addBodyParameter(Conf.RLname, RLname)
//                .addBodyParameter(Conf.RST,RST)
//                .addBodyParameter(Conf.RCity, RCity)
//                .addBodyParameter(Conf.RGov, RGov)
//                .addBodyParameter(Conf.RTel, RTel)
//                .addBodyParameter(Conf.RMail, RMail)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsString(new StringRequestListener()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        if(response.equalsIgnoreCase("123b:0;4")){
//                            Toast.makeText(FolowingProducts.this, "Your Result Recorded", Toast.LENGTH_SHORT).show();
//                            Build();
//
//                        }
//                        else if(response.equalsIgnoreCase("nooo")){
//
//                            Log.e("res",response);
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError)
//                    {
//                        Log.e("err",anError.toString());
//
//
//                    }
//                });
//
//
//    }
//    public void Build()
//    {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
//        alertDialog.setTitle(" أسواق الشريف");
//        alertDialog.setMessage("شكرا لاستخدامكم أسواق الشريف وسيتم التوصيل ف أسرع وقت  ");
//
//
//        alertDialog.setPositiveButton("موافق",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                       // dialog.cancel();
//                        startActivity(new Intent(FolowingProducts.this,MainActivity.class));
//                    }
//                });
//
//
//
//
//        alertDialog.setNegativeButton("إلغاء",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//        alertDialog.show();
//
//    }
//    public void testSendOrder(){
//
//        Log.v("aaaaaaaaaaaaaaaaaa",   "1");
//
//        final List<RetriveOrder> retriveOrders =new ArrayList<>();
//        Order order =new Order();
//        order.setPaymentMethod("الدفع عند التسليم");
//
//        order.setBilling(billing);
//        order.setLineItems(lineItems);
//        order.setSetPaid(false);
//        Log.v("aaaaaaaaaaaaaaaaaa",   "2");
//
//
//       String url = "http://mo-mart.com/wp-json/wc/v2/";
//       // String url = "http://fictionapps.com/demo/souq/wp-json/wc/v2/";
//
//      //  OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer("ck_baeba18c4bf961af446ad4aad1aaf50b46b16e95",
//        //        "cs_10467bc00edd399bcd24e21f7f3c338c02975dd0");
//       OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer("ck_c1d1b2f48c0dcd6a7b2af63b37bf07a1056cc4e0",
//               "cs_138bcc416ae4442f81031320897b58791b94f456");
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new SigningInterceptor(consumer))
//                .build();
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//
//        InterfaceRetro service = retrofit.create(InterfaceRetro.class);
//
//        service.creatOrder(order).enqueue(new Callback<List<RetriveOrder>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<RetriveOrder>> call, @NonNull Response<List<RetriveOrder>> response) {
//                Log.v("aaaaaaaaaaaaaaaaaa",   "done");
//
//                if (response.body() != null) {
//                    retriveOrders.addAll(response.body());
//                    Log.v("aaaaaaaaaaaaaaaaaa", retriveOrders.size()+  " >> size");
//
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<RetriveOrder>> call, @NonNull Throwable t) {
//                Log.v("Error", t.getMessage() + "");
//                Log.v("aaaaaaaaaaaaaaaaaa", t.getMessage() + "");
//
//            }
//        });
//
//    }
//
//}
