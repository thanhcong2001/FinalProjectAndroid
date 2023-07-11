package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalproject.Register.UserRepository;
import com.example.finalproject.api.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeMorePage extends AppCompatActivity {
    UserService userService;
    ArrayList<ChildModelClass> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_page);
        userService = UserRepository.getUserService();
        recyclerView = findViewById(R.id.rcv_book);
        bookAdapter = new BookAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        userService.getAllBook().enqueue(new Callback<BookRecycleView[]>() {
            @Override
            public void onResponse(Call<BookRecycleView[]> call, Response<BookRecycleView[]> response) {

                BookRecycleView[] books = response.body();
                for(BookRecycleView book : books){
                    bookList.add(new ChildModelClass(book.getBook_Id(),book.getImage_URL(),book.getBook_Title(),book.getBook_Author()));
                }
                bookAdapter.setData(bookList);
                recyclerView.setAdapter(bookAdapter);
            }

            @Override
            public void onFailure(Call<BookRecycleView[]> call, Throwable t) {

            }
        });

    }
}