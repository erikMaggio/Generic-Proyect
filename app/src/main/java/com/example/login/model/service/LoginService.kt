package com.example.login.model.service

import com.example.login.model.response.*
import com.example.login.model.response.LoginResponse
import com.example.login.model.response.Register
import com.example.login.model.response.SignUpResponse
import com.example.login.model.response.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    suspend fun postLogin(
        @Body user: User
    ): Response<LoginResponse>

    @POST("signup")
    suspend fun postSignUp(
        @Body register : Register
    ) : Response<SignUpResponse>
}

