package com.example.finalproject.api;

import com.example.finalproject.login.LoginRequest;
import com.example.finalproject.login.LoginResponse;
import com.example.finalproject.order.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {

    @POST("api/order/createOrder/")
    Call<Order> createOrder(@Body Order order);
}
