package com.example.hp.aswaq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubCat extends AppCompatActivity
{
    ListView listView;
    String S_id;
    ArrayList<String> Sub_name;
    ArrayList<String>tax_id;
    ArrayList<String>S_imgList;
    SubAdapter subAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat);
        AndroidNetworking.initialize(getApplicationContext());

        listView=findViewById(R.id.sublist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String taxo_id=tax_id.get(i);
                Toast.makeText(SubCat.this, taxo_id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ViewItems.class);
                intent.putExtra(Conf.term_taxonomy_id,taxo_id);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        S_id = intent.getStringExtra(Conf.term_id);


        Sub_name = new ArrayList<>();
        tax_id = new ArrayList<>();
        S_imgList=new ArrayList<>();
        GetSub();

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
            startActivity(new Intent(SubCat.this,Cart.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public  void GetSub()
    {
     // AndroidNetworking.get()
        class GetSub extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SubCat.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                ShowSub(s);
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.list_tv,Sub_name);
//                listView.setAdapter(arrayAdapter);

                subAdapter=new SubAdapter(getApplicationContext(),Sub_name,S_imgList);
                listView.setAdapter(subAdapter);




            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Conf.Sub_url,S_id);
                return s;

            }
        }
        GetSub gr = new GetSub();
        gr.execute();


    }
    public void ShowSub(String json)
    {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray result = jsonObject.getJSONArray(Conf.sub_arr);
            if (result.length() == 0)
            {
                Intent intent = new Intent(getApplicationContext(), ViewItems.class);
                intent.putExtra(Conf.term_taxonomy_id,S_id);
                startActivity(intent);


            } else
                {


                 for (int i = 0; i < result.length(); i++) {
                     JSONObject object = result.getJSONObject(i);
                     Sub_name.add(object.getString(Conf.Tax_name));
                     S_imgList.add(object.getString(Conf.Tax_guid));
                     tax_id.add(object.getString(Conf.term_taxonomy_id));


                     //Sterm_slug.add(object.getString(Conf.Sub_slug));
//                    JSONObject object = result.getJSONObject(i);
//                    Sub_name.add(object.getString(Conf.Tax_name));
//                    tax_id.add(object.getString(Conf.term_taxonomy_id));
//                   // Toast.makeText(this, "nnn" + Sub_name, Toast.LENGTH_SHORT).show();

                 }
                }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
