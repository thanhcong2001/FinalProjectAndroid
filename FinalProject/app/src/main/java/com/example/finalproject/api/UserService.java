package com.example.finalproject.api;
import com.example.finalproject.Register.User;
import com.example.finalproject.login.LoginRequest;
import com.example.finalproject.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/user/login/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("api/user/createUser")
    Call<User> signUp(@Body User trainee);
}
