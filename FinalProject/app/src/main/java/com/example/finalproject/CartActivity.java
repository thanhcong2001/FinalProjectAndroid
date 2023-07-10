package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.List;

public class CartActivity extends AppCompatActivity {

    List<CartItemModel> items;
    private RecyclerView recyclerCart;

    private ImageView btnBack;
    private TextView subTotal, shipping, totalPrice;
    private CartModel cartModel;
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
}