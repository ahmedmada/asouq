package com.example.hp.aswaq;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetails extends AppCompatActivity implements View.OnClickListener
{
    ImageView itemImg;
    TextView name,price_before,price_after,details;
    Button Add;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        FloatingActionButton cart = (FloatingActionButton) findViewById(R.id.crt);
        FloatingActionButton fav = (FloatingActionButton) findViewById(R.id.fav);
        name=findViewById(R.id.nameTV);
        price_before=findViewById(R.id.priceTV);
        price_after=findViewById(R.id.priceafter);
        details=findViewById(R.id.detailsTV);
        Add=findViewById(R.id.add);
        itemImg=findViewById(R.id.itemIM);


        name.setOnClickListener(this);
        price_after.setOnClickListener(this);
        price_before.setOnClickListener(this);
        details.setOnClickListener(this);
        Add.setOnClickListener(this);
        itemImg.setOnClickListener(this);
        cart.setOnClickListener(this);
        fav.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.crt:
                Toast.makeText(this, "add to cart", Toast.LENGTH_SHORT).show();

                break;
            case R.id.fav:
                Toast.makeText(this, "Add to fav", Toast.LENGTH_SHORT).show();
                break;



        }

    }
}
