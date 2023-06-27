package com.example.finalproject.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Register.SignUp;
import com.example.finalproject.api.ApiClient;
import com.example.finalproject.MainPage;
import com.example.finalproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    EditText username, password;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        username = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        btn = findViewById(R.id.button);
        TextView create = findViewById(R.id.textView3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(LoginPage.this, "Username / Password Required", Toast.LENGTH_LONG).show();
                } else {
                    //proceed to login
                    login();
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUp = new Intent(LoginPage.this,SignUp.class);
                startActivity(SignUp);
            }
        });
    }
        public void login(){
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUser_Account(username.getText().toString());
            loginRequest.setUser_Password(password.getText().toString());
            Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()){
                        Intent intentLogin = new Intent(LoginPage.this,MainPage.class);
                        startActivity(intentLogin);
                        LoginResponse loginResponse = response.body();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            }
                        },700);
                    }else{
                        Toast.makeText(LoginPage.this,"Wrong Username or password ", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginPage.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
