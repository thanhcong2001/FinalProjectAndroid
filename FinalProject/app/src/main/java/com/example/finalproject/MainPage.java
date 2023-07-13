package com.example.finalproject;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;
import javax.swing.text.View;

import com.example.finalproject.Register.User;
import com.example.finalproject.Register.UserRepository;
import com.example.finalproject.api.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
     RecyclerView recyclerView;
     GridLayoutManager gridLayoutManager;
     User user;

    ArrayList<ParentModelClass> parentModelClassArrayList;
    ArrayList<ChildModelClass> childModelClassArrayList;
    ArrayList<BookRecycleView> favoriteList;
    ArrayList<BookRecycleView> recentlyWatchedList;
    ArrayList<BookRecycleView> latesList;

    ArrayList<BookRecycleView> comicList;

    ArrayList<BookRecycleView> mysteryList;

    ArrayList<BookRecycleView> horrorList;
    UserService userService;

    List<BookRecycleView> listBook;
    BottomNavigationView nav;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        userService = UserRepository.getUserService();
        recyclerView =findViewById(R.id.rcv_book);
        childModelClassArrayList = new ArrayList<>();
        favoriteList= new ArrayList<BookRecycleView>();
        recentlyWatchedList= new ArrayList<BookRecycleView>();
        latesList = new ArrayList<BookRecycleView>();
        comicList = new ArrayList<BookRecycleView>();
        mysteryList = new ArrayList<BookRecycleView>();
        horrorList = new ArrayList<BookRecycleView>();
        parentModelClassArrayList = new ArrayList<>();
        //Bottom nevigation
        nav= findViewById(R.id.nav_bar);
        nav.setSelectedItemId(R.id.home);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.book) {
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
                                latesList.add(book);
                                break;
                            case "2":
                                comicList.add(book);
                                break;
                            case "3":
                                favoriteList.add(book);
                                break;
                            case "4":
                                mysteryList.add(book);
                                break;
                            case "5":
                                recentlyWatchedList.add(book);
                                break;
                            case "6":
                                horrorList.add(book);
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

