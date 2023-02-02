package com.example.login.model.dataSource

import com.example.login.model.response.*
import com.example.login.model.service.LoginService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://nvfsolutions.com:8081/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serviceImp = retrofit.create(LoginService::class.java)


    suspend fun postLogin(user: User): Response<LoginResponse> {
        return serviceImp.postLogin(user)
    }

    suspend fun postSignUp(register: Register): Response<SignUpResponse> {
        return serviceImp.postSignUp(register)

//        if (value.code() == 200) {
//            return Result.success(value.body())
//        }else{
//            // suponiendo que sea 500
//            return Result.error(null, message = "error")
//        }
    }

    suspend fun postTest(): Response<testResponse> {
        return serviceImp.getTest()
    }


}


