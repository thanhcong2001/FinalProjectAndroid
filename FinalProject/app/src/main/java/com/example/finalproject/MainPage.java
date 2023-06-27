package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.databinding.ActivityMainPageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
     RecyclerView recyclerView;
     GridLayoutManager gridLayoutManager;
    ArrayList<ParentModelClass> parentModelClassArrayList;
    ArrayList<ChildModelClass> childModelClassArrayList;
    ArrayList<ChildModelClass> favoriteList;
    ArrayList<ChildModelClass> recentlyWatchedList;
    ArrayList<ChildModelClass> latesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        recyclerView =findViewById(R.id.rcv_book);
        childModelClassArrayList = new ArrayList<>();
        favoriteList= new ArrayList<>();
        recentlyWatchedList= new ArrayList<>();
        latesList = new ArrayList<>();
        parentModelClassArrayList = new ArrayList<>();
        String data = "";
        if (getIntent() != null) {
            data = getIntent().getStringExtra("name");
        }
        TextView textView = findViewById(R.id.textView7);
        textView.setText("Hello "+ data);

        List<Book> list = new ArrayList<>();
        latesList.add(new ChildModelClass(R.drawable.book3));
        latesList.add(new ChildModelClass(R.drawable.book3));
        latesList.add(new ChildModelClass(R.drawable.book3));
        latesList.add(new ChildModelClass(R.drawable.book3));
        latesList.add(new ChildModelClass(R.drawable.book3));
        latesList.add(new ChildModelClass(R.drawable.book3));
        latesList.add(new ChildModelClass(R.drawable.book3));

        parentModelClassArrayList.add(new ParentModelClass("Lastes Book",latesList));

        recentlyWatchedList.add(new ChildModelClass(R.drawable.li3));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.li3));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.li3));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.li3));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.li3));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.li3));
        recentlyWatchedList.add(new ChildModelClass(R.drawable.li3));

        parentModelClassArrayList.add(new ParentModelClass("Recent Book",recentlyWatchedList));

        favoriteList.add(new ChildModelClass(R.drawable.pi2));
        favoriteList.add(new ChildModelClass(R.drawable.pi2));
        favoriteList.add(new ChildModelClass(R.drawable.pi2));
        favoriteList.add(new ChildModelClass(R.drawable.pi2));
        favoriteList.add(new ChildModelClass(R.drawable.pi2));
        favoriteList.add(new ChildModelClass(R.drawable.pi2));
        favoriteList.add(new ChildModelClass(R.drawable.pi2));

        parentModelClassArrayList.add(new ParentModelClass("Favorite Book",favoriteList));

        childModelClassArrayList.clear();
        childModelClassArrayList.add(new ChildModelClass(R.drawable.li3));
        childModelClassArrayList.add(new ChildModelClass(R.drawable.li3));
        childModelClassArrayList.add(new ChildModelClass(R.drawable.li3));
        childModelClassArrayList.add(new ChildModelClass(R.drawable.li3));
        childModelClassArrayList.add(new ChildModelClass(R.drawable.li3));
        childModelClassArrayList.add(new ChildModelClass(R.drawable.li3 ));

        parentModelClassArrayList.add(new ParentModelClass("RecentLy",childModelClassArrayList));
        parentModelClassArrayList.add(new ParentModelClass("Great",childModelClassArrayList));
        parentModelClassArrayList.add(new ParentModelClass("Fine",childModelClassArrayList));
        parentModelClassArrayList.add(new ParentModelClass("Great",childModelClassArrayList));

        ParentAdapter parentAdapter = new ParentAdapter(parentModelClassArrayList,MainPage.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(parentAdapter);
        parentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}