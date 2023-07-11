package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.Register.UserRepository;
import com.example.finalproject.api.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetail extends AppCompatActivity  {
    ImageView imageView;
    TextView bookTitle,authorName,price,detail;
    Button button;
    BookRecycleView book;

    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        userService = UserRepository.getUserService();
        imageView = findViewById(R.id.ivBook);
        bookTitle = findViewById(R.id.tvBookTitle);
        authorName = findViewById(R.id.tvAuthor);
        price = findViewById(R.id.tvPrice);
        detail = findViewById(R.id.tvDetail);
        Bundle bundle = getIntent().getExtras();
        String book_Id = (String)bundle.get("book_Id");
        userService.getBookDetail(book_Id).enqueue(new Callback<BookRecycleView>() {
            @Override
            public void onResponse(Call<BookRecycleView> call, Response<BookRecycleView> response) {
                book = response.body();
                if(book != null){
                    Glide.with(BookDetail.this).load(book.getImage_URL()).into(imageView);
                    bookTitle.setText(book.getBook_Title());
                    authorName.setText(book.getBook_Author());
                    price.setText(book.getBook_Price());
                    detail.setText(book.getBook_Description());
                }
            }
            @Override
            public void onFailure(Call<BookRecycleView> call, Throwable t) {
                Toast.makeText(BookDetail.this,"Error!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}