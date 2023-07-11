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

import com.android.car.ui.toolbar.TabLayout;
import com.bumptech.glide.Glide;
import com.example.finalproject.Register.UserRepository;
import com.example.finalproject.api.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetail extends AppCompatActivity  {
    ImageView imageView;
    TextView bookTitle,authorName,price,publicYear,type,description;
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
        publicYear = findViewById(R.id.tvPublicYear);
        type = findViewById(R.id.tvBookType);
        description = findViewById(R.id.tvDetail);
        button = findViewById(R.id.btnAddToCart);
        Bundle bundle = getIntent().getExtras();
        book = (BookRecycleView) bundle.get("bookObject");
        if(book != null){
            Glide.with(BookDetail.this).load(book.getImage_URL()).into(imageView);
            bookTitle.setText(book.getBook_Title());
            authorName.setText(book.getBook_Author());
            price.setText(String.valueOf(book.getBook_Price()));
            publicYear.setText(String.valueOf(book.getBook_Year_Public()));
            type.setText(book.getCategory_Name());
            description.setText(book.getBook_Description());
        }
    }
}