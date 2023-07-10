package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.Register.UserRepository;
import com.example.finalproject.api.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextSearch;
    private AppCompatButton buttonSearch;
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

        editTextSearch = findViewById(R.id.editTextText);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);

        //Bottom nevigation
        nav= findViewById(R.id.nav_bar);
        nav.setSelectedItemId(R.id.home);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.book) {
                    startActivity(new Intent(MainPage.this, ListStore.class));
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
        if (v.getId() == R.id.buttonSearch) {
            String searchText = editTextSearch.getText().toString().trim();
            performSearch(searchText);
        }
    }
    private void performSearch(String searchText) {
        if (searchText.isEmpty()) {
            // Clear the search results and show the original list of products
            ParentAdapter parentAdapter = new ParentAdapter(parentModelClassArrayList, MainPage.this);
            recyclerView.setAdapter(parentAdapter);
        } else {
            ArrayList<ChildModelClass> searchResults = new ArrayList<>();
            for (ParentModelClass parentModel : parentModelClassArrayList) {
                for (ChildModelClass childModel : parentModel.getChildModelClassList()) {
                    if (childModel.getBook_Title().toLowerCase().contains(searchText.toLowerCase())) {
                        searchResults.add(childModel);
                    }
                }
            }

            ArrayList<ParentModelClass> updatedList = new ArrayList<>();
            if (!searchResults.isEmpty()) {
                // Add the "Search Results" category only if there are matching results
                ParentModelClass searchParentModel = new ParentModelClass("Search Results", searchResults);
                updatedList.add(searchParentModel);
            }

            // Update the RecyclerView with the search results
            ParentAdapter parentAdapter = new ParentAdapter(updatedList, MainPage.this);
            recyclerView.setAdapter(parentAdapter);

            // Hide the "See more" TextView in all child items
            View childItem = recyclerView.getLayoutManager().findViewByPosition(0);
            if (childItem != null) {
                TextView seeMoreTextView = childItem.findViewById(R.id.textView_book1);
                seeMoreTextView.setVisibility(View.GONE);
            }
        }
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
}
