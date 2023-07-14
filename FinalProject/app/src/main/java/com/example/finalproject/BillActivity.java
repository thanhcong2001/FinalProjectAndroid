package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.adapter.CartAdapter;
import com.example.finalproject.model.CartItemModel;
import com.example.finalproject.model.CartModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class BillActivity extends AppCompatActivity {

    List<CartItemModel> items;
    private RecyclerView recyclerProducts;

    private ImageView btnBack;
    private TextView name, address, phone, subTotal, shipping, totalPrice;
    private CartModel cartModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        recyclerProducts = findViewById(R.id.recycler_order_products);
        name = findViewById(R.id.user_name);
        address = findViewById(R.id.user_address);
        phone = findViewById(R.id.user_phone);
        subTotal = findViewById(R.id.subtotal);
        shipping = findViewById(R.id.shipping);
        totalPrice = findViewById(R.id.total_price);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProducts.setLayoutManager(layoutManager);

        loadJSONFromInternalData();

        if(cartModel != null) {
            items = cartModel.getItemModels();
            subTotal.setText("$" + Double.toString(cartModel.getTotalPrice()));
            shipping.setText("$" + "5");
            double total = cartModel.getTotalPrice() + 5;
            totalPrice.setText("$" + Double.toString(total));

            CartAdapter adapter = new CartAdapter(BillActivity.this, cartModel);
            recyclerProducts.setAdapter(adapter);
            getApplicationContext().deleteFile("cart.json");
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