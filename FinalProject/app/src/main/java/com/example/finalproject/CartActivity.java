package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.adapter.CartAdapter;
import com.example.finalproject.eventbus.MyUpdateCartEvent;
import com.example.finalproject.model.CartItemModel;
import com.example.finalproject.model.CartModel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    List<CartItemModel> items;
    private RecyclerView recyclerCart;

    private ImageView btnBack;
    private TextView subTotal, shipping, totalPrice;
    private CartModel cartModel;
    private Button btnCheckOut;
    String URL = "https://regres.in/api/users?page=1";

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if(EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class))
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUpdateCart(MyUpdateCartEvent event){
        loadJSONFromInternalData();
        subTotal.setText("$" + Double.toString(cartModel.getTotalPrice()));
        shipping.setText("$" + "5");
        double total = cartModel.getTotalPrice() + 5;
        totalPrice.setText("$" + Double.toString(total));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerCart = findViewById(R.id.recycler_cart);
        subTotal = findViewById(R.id.subtotal);
        shipping = findViewById(R.id.shipping);
        totalPrice = findViewById(R.id.total_price);
        btnBack = findViewById(R.id.btnBack);
        btnCheckOut = findViewById(R.id.btn_checkout);
        btnBack.setOnClickListener(v -> finish());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(layoutManager);

        loadJSONFromInternalData();

        if(cartModel != null) {
            items = cartModel.getItemModels();
            subTotal.setText("$" + Double.toString(cartModel.getTotalPrice()));
            shipping.setText("$" + "5");
            double total = cartModel.getTotalPrice() + 5;
            totalPrice.setText("$" + Double.toString(total));

            CartAdapter adapter = new CartAdapter(CartActivity.this, cartModel);
            recyclerCart.setAdapter(adapter);
        }else{
            subTotal.setText("$" + "0");
            shipping.setText("$" + "0");
            totalPrice.setText("$" + "0");
        }

        btnCheckOut.setOnClickListener(v -> {
//            boolean flag = saveOrderToDatabase();
//            if(flag) {
//
//                startActivity(new Intent(CartActivity.this, BillActivity.class));
//            }
        });
    }

    private void loadJSONFromInternalData(){
        try {
            File file = new File(getApplicationContext().getFilesDir(), "cart.json");
            if (file.exists()) {
                InputStream is = getApplicationContext().openFileInput("cart.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonContent = stringBuilder.toString();
                if (!jsonContent.isEmpty()) {
                    Gson gson = new Gson();
                    cartModel = gson.fromJson(jsonContent, CartModel.class);
                    Log.d("CartModel Data", jsonContent);
                }
                is.close();
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

//    private boolean saveOrderToDatabase(){
//        boolean flag = false;
//        Order order = new Order();
//        order.setUser_Id(cartModel.getUserID());
//        order.setOrder_Customer_Name("");
//        order.setOrder_Customer_Address("");
//        order.setOrder_Customer_Phone("");
//        order.setOrder_Date(Calendar.getInstance().getTime());
//        order.setOrder_Quantity(cartModel.getTotalNumber());
//        order.setOrder_Amount(cartModel.getTotalPrice());
//        List<CartItemModel> items = cartModel.getItemModels();
//        try {
//            Call<Order> call = orderService.createOrder(order);
//            call.enqueue(new Callback<Order>() {
//                @Override
//                public void onResponse(Call<Order> call, Response<Order> response) {
//                    if (response.body() != null) {
//                        for (CartItemModel item: items) {
//                            OrderDetail orderDetail = new OrderDetail();
//                            orderDetail.setOrder_Id(response.body().getOrder_Id());
//                            orderDetail.setBook_Id(item.getId());
//                            int number = item.getNumber();
//                            double price = item.getPrice();
//                            orderDetail.setOrder_Detail_Quantity(number);
//                            orderDetail.setOrder_Detail_Amount(price * number);
//                            orderDetail.setOrder_Detail_Price(price);
//                            Call<OrderDetail> call1 = orderService.createOrderDetail(orderDetail);
//                            call1.enqueue(new Callback<OrderDetail>() {
//                                @Override
//                                public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<OrderDetail> call, Throwable t) {
//
//                                }
//                            });
//                        }
//                        Toast.makeText(CartActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                @Override
//                public void onFailure(Call<Order> call, Throwable t) {
//                    Toast.makeText(CartActivity.this, "Save Failed", Toast.LENGTH_SHORT).show();
//                }
//            });
//            return true;
//        } catch (Exception e) {
//            Log.d("Loi", e.getMessage());
//        }
//        return false;
//    }
}