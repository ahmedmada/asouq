package com.example.hp.aswaq.Interface;

import com.example.hp.aswaq.Order.Order;
import com.example.hp.aswaq.RetriveOrder.RetriveOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceRetro {
    @POST("orders")
    Call<List<RetriveOrder>> creatOrder(@Body Order order);

}
