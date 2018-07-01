package com.example.hp.aswaq;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.SearchManager;



import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener
{
    ImageView best,header;
    GridView categories;
    ExpandableHeightGridView grid;
    TextView besttext,lOGtV;
    String offersid,offerslug;
    private String JSON_STRING;
    CatModel gridItemModel=new CatModel();
    ArrayList<String> ImageUrlList;
    ArrayList<String>Cat_name;
    ArrayList<String>termid;
    ArrayList<String>term_slug;
    ArrayList<String>sliderimg;
    gridcat_adapter adapter;
    ViewPager viewPager;
    ViewPagerAdapter adapterr;
    SearchView sv;
    HashMap<String,String> map_id = new HashMap<>();

    private SwipeRefreshLayout mRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

        TextView profilename = (TextView) headerview.findViewById(R.id.lo);
        profilename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Regisetration.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_layout);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                GetmenusPhotos();

            }
        });

        var();
//        GetmenusPhotos();

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetmenusPhotos();
    }

    protected void var()
    {
        best=findViewById(R.id.bestoffer);
        besttext=findViewById(R.id.bestTV);
        // header=findViewById(R.id.slider);

        //categories=findViewById(R.id.grid);
        grid=findViewById(R.id.myId);
        grid.setExpanded(true);

        best.setOnClickListener(this);

        ImageUrlList = new ArrayList<>();
        Cat_name = new ArrayList<>();
        termid = new ArrayList<>();
        term_slug=new ArrayList<>();
        sliderimg=new ArrayList<>();
        viewPager  = findViewById(R.id.view_pager);




        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                String t_id=termid.get(i);
                String t_slug=term_slug.get(i);
                //Toast.makeText(MainActivity.this, t_id, Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(getApplicationContext(), SubCat.class);
                inte.putExtra(Conf.term_id,t_id);
                inte.putExtra("mainslug",t_slug);
                startActivity(inte);
            }
        });

        best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubCat.class);
                intent.putExtra(Conf.term_id,offersid);
                intent.putExtra("PMS",offerslug);
                intent.putExtra("PSS","R");

                startActivity(intent);

            }
        });









    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);




        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        sv = (SearchView) menu.findItem(R.id.action_search).getActionView();


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("k",query);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s)
            {
                Log.e("s",s);


                return false;
            }
        });
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN);







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

            return true;
        }
        if(id==R.id.action_search){




        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            startActivity(new Intent(MainActivity.this,Cart.class));
            // Handle the camera action
        } else if (id == R.id.nav_fav) {
            startActivity(new Intent(MainActivity.this,Favourit.class));

        } else if (id == R.id.nav_share) {
            //  shareTextUrl();

        } else if (id == R.id.nav_orders) {

        }else if(id==R.id.lo){
            startActivity(new Intent(MainActivity.this,Regisetration.class));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view)
    {

    }
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "حملوا التطبيق الأن !");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=test.faceweek");

        startActivity(Intent.createChooser(share, "Share link!"));
    }


    int checkFirst = 0;
    public  void GetmenusPhotos()
    {
        class GetRest extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mRefreshLayout.setRefreshing(true);
                loading = ProgressDialog.show(MainActivity.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                if (checkFirst == 0){
//                    checkFirst=1;
//                    GetmenusPhotos();
//                }else {

                    loading.dismiss();
                    mRefreshLayout.setRefreshing(false);
                    ShowPhotos(s);
                    adapter = new gridcat_adapter(getApplicationContext(), ImageUrlList, Cat_name);
                    Log.v("aaaaaaaaaaaaaaaaaaa",ImageUrlList.size()+"");
                    Log.v("aaaaaaaaaaaaaaaaaaa",Cat_name.size()+"");
                    Log.v("aaaaaaaaaaaaaaaaaaa","*******************");
                    grid.setAdapter(adapter);
                    for (int x = 0; x < Cat_name.size(); x++) {
                        if (Cat_name.get(x).equals("مجلة العروض")) {
                            besttext.setText(Cat_name.get(x));
                            besttext.setWidth(best.getWidth());
                            offersid = termid.get(x);
                            offerslug = term_slug.get(x);


                            Picasso.with(best.getContext())
                                    .load(ImageUrlList.get(x))
                                    .noFade().resize(best.getWidth(), 200)
                                    .centerCrop()
                                    .into(best);

                        }
                    }


                    adapterr = new ViewPagerAdapter(getApplicationContext(), sliderimg);
                    viewPager.setAdapter(adapterr);

//                }

            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Conf.cat_url);
                return s;

            }
        }
        GetRest gr = new GetRest();
        gr.execute();


    }
    public void ShowPhotos(String json)
    {


        ImageUrlList = new ArrayList<>();
        Cat_name = new ArrayList<>();
        termid = new ArrayList<>();
        term_slug = new ArrayList<>();
        map_id = new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray slides = jsonObject.getJSONArray(Conf.slid_arr);
            Log.e("slides",slides.toString());
            for(int i = 0; i<slides.length(); i++)
            {
                JSONObject slid_object = slides.getJSONObject(i);
//
                sliderimg.add(slid_object.getString(Conf.guid));


            }
            Log.e("g",sliderimg.toString());




            JSONArray result = jsonObject.getJSONArray(Conf.cat_array);
            Log.e("result",result.toString());

            for(int i = 0; i<result.length(); i++)
            {
                JSONObject object = result.getJSONObject(i);
//
                ImageUrlList.add(object.getString(Conf.guid));
                Cat_name.add(object.getString(Conf.name));
                termid.add(object.getString(Conf.term_id));
                term_slug.add(object.getString(Conf.terms_slug));


                map_id.put(Conf.term_id,object.getString(Conf.term_id));
                map_id.put(Conf.name,object.getString(Conf.name));

            }











        } catch (JSONException e) {
            e.printStackTrace();
        }

    }







}
