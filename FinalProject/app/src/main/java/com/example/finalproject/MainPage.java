package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.finalproject.Register.UserRepository;
import com.example.finalproject.api.UserService;
import com.example.finalproject.eventbus.MyUpdateCartEvent;
import com.example.finalproject.model.CartItemModel;
import com.example.finalproject.model.CartModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
     RecyclerView recyclerView;
     GridLayoutManager gridLayoutManager;

    ArrayList<ParentModelClass> parentModelClassArrayList;
    ArrayList<ChildModelClass> childModelClassArrayList;
    ArrayList<ChildModelClass> favoriteList;
    ArrayList<ChildModelClass> recentlyWatchedList;
    ArrayList<ChildModelClass> latesList;

    ArrayList<ChildModelClass> comicList;
    UserService userService;

    List<BookRecycleView> listBook;
    BottomNavigationView nav;
    private NotificationBadge badge;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//        countCartItem();
//    }
//
//    @Override
//    protected void onStop() {
//        if(EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class))
//            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void onUpdateCart(MyUpdateCartEvent event){
//        countCartItem();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        userService = UserRepository.getUserService();
        recyclerView =findViewById(R.id.rcv_book);
        childModelClassArrayList = new ArrayList<>();
        favoriteList= new ArrayList<>();
        recentlyWatchedList= new ArrayList<>();
        latesList = new ArrayList<>();
        comicList = new ArrayList<>();
        parentModelClassArrayList = new ArrayList<>();
        //Bottom nevigation
        nav= findViewById(R.id.nav_bar);
        nav.setSelectedItemId(R.id.home);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.book) {
                    startActivity(new Intent(MainPage.this, GoogleMaps.class));
                    return true;
                }else if (item.getItemId() == R.id.cart){
                    startActivity(new Intent(MainPage.this, CartActivity.class));
                    return true;
                }
                return false;
            }
        });
        //Get userName to display
        String data = "";
        if (getIntent() != null) {
            data = getIntent().getStringExtra("name");
        }
        TextView textView = findViewById(R.id.textView7);
        textView.setText("Hello "+ data);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GetAll();
        addFakeToCart(1);
    }

    @Override
    public void onClick(View v) {
    }
    public void GetAll(){
        Call<BookRecycleView[]> call = (Call<BookRecycleView[]>) userService.getAllBook();
        call.enqueue(new Callback<BookRecycleView[]>() {
            @Override
            public void onResponse(Call<BookRecycleView[]> call, Response<BookRecycleView[]> response) {
                BookRecycleView[] books = response.body();
                if(books == null){
                    return;
                }
                for (BookRecycleView book : books) {
                    switch (book.getCategory_Id()) {
                        case "1":
                            latesList.add(new ChildModelClass(book.getImage_URL(), book.getBook_Title(), book.getBook_Author()));
                            break;
                        case "2":
                            comicList.add(new ChildModelClass(book.getImage_URL(), book.getBook_Title(), book.getBook_Author()));
                            break;
                        case "5":
                            recentlyWatchedList.add(new ChildModelClass(book.getImage_URL(), book.getBook_Title(), book.getBook_Author()));
                            break;
                        case "3":
                            favoriteList.add(new ChildModelClass(book.getImage_URL(), book.getBook_Title(), book.getBook_Author()));
                            break;
                    }
                }
                parentModelClassArrayList.add(new ParentModelClass("Novel Book",latesList));
                parentModelClassArrayList.add(new ParentModelClass("Detective Book",recentlyWatchedList));
                parentModelClassArrayList.add(new ParentModelClass("Romance Book",favoriteList));
                parentModelClassArrayList.add(new ParentModelClass("Comic Book",comicList));
                ParentAdapter parentAdapter = new ParentAdapter(parentModelClassArrayList,MainPage.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainPage.this));
                recyclerView.setAdapter(parentAdapter);
                parentAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<BookRecycleView[]> call, Throwable t) {

            }
        });
    }

    private void countCartItem() {
        try {
            File file = new File(getApplicationContext().getFilesDir(), "cart.json");
            if (!file.exists()){
                badge.setNumber(0);
            }else {
                int total = 0;
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
                    CartModel cartModel = gson.fromJson(jsonContent, CartModel.class);
                    badge.setNumber(cartModel.getTotalNumber());
                } else {
                    badge.setNumber(0);
                }

                is.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addFakeToCart(int userID) {
        JSONArray jsonArray;
        Gson gson = new Gson();
        try {
            File file = new File(getApplicationContext().getFilesDir(), "cart.json");
            if (!file.exists()){
                file.createNewFile();
                List<CartItemModel> cartItems = new ArrayList<>();
                cartItems.add(new CartItemModel(1, 1,
                        "abc", R.drawable.book1, 10.99));
                cartItems.add(new CartItemModel(2, 1,
                        "xyz", R.drawable.book3, 20.99));
                CartModel cartModel = new CartModel(userID, cartItems);
                String cartModelJson = gson.toJson(cartModel);
                OutputStream os = getApplicationContext().openFileOutput("cart.json", Context.MODE_PRIVATE);
                os.write(cartModelJson.getBytes());
                os.close();
                Log.d("CartModel Data", cartModelJson);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

