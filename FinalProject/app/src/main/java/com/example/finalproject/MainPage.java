package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.finalproject.Register.User;
import com.example.finalproject.Register.UserRepository;
import com.example.finalproject.api.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
     RecyclerView recyclerView;
     GridLayoutManager gridLayoutManager;
     User user;

    ArrayList<ParentModelClass> parentModelClassArrayList;
    ArrayList<ChildModelClass> childModelClassArrayList;
    ArrayList<ChildModelClass> favoriteList;
    ArrayList<ChildModelClass> recentlyWatchedList;
    ArrayList<ChildModelClass> latesList;

    ArrayList<ChildModelClass> comicList;

    ArrayList<ChildModelClass> mysteryList;

    ArrayList<ChildModelClass> horrorList;
    UserService userService;

    List<BookRecycleView> listBook;
    BottomNavigationView nav;
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
        mysteryList = new ArrayList<>();
        horrorList = new ArrayList<>();
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
                } else if (item.getItemId()==R.id.setting) {
                    startActivity(new Intent(MainPage.this, Setting.class));
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
                            case "3":
                                favoriteList.add(new ChildModelClass(book.getImage_URL(), book.getBook_Title(), book.getBook_Author()));
                                break;
                            case "4":
                                mysteryList.add(new ChildModelClass(book.getImage_URL(), book.getBook_Title(), book.getBook_Author()));
                                break;
                            case "5":
                                recentlyWatchedList.add(new ChildModelClass(book.getImage_URL(), book.getBook_Title(), book.getBook_Author()));
                                break;
                            case "6":
                                horrorList.add(new ChildModelClass(book.getImage_URL(), book.getBook_Title(), book.getBook_Author()));
                                break;
                        }
                    }
                parentModelClassArrayList.add(new ParentModelClass("Novel Book",latesList));
                parentModelClassArrayList.add(new ParentModelClass("Detective Book",recentlyWatchedList));
                parentModelClassArrayList.add(new ParentModelClass("Romance Book",favoriteList));
                parentModelClassArrayList.add(new ParentModelClass("Comic Book",comicList));
                parentModelClassArrayList.add(new ParentModelClass("Mystery Book",mysteryList));
                parentModelClassArrayList.add(new ParentModelClass("Horror Book",horrorList));
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
}

