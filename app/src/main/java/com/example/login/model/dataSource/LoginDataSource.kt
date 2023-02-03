package com.example.login.model.dataSource

import com.example.login.model.response.*
import com.example.login.model.service.LoginService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.login.utils.Result

class LoginDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://nvfsolutions.com:8081/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serviceImp = retrofit.create(LoginService::class.java)

    suspend fun postLogin(user: User): Response<LoginResponse> {
        return serviceImp.postLogin(user)
    }

    suspend fun postSignUp(register: Register): Result<SignUpResponse> {
        val call: Response<SignUpResponse> = serviceImp.postSignUp(register)

        return when (call.code()) {
            200 -> {
                Result.success(call.body(),"200")
            }
            401 -> {
                Result.error(message = "401")
            }
            500 -> {
                Result.error(message = "500")
            }
            404 -> {
                Result.error(message = "404")
            }

            else -> {
                Result.error(message = "0")
            }
        }
    }

    suspend fun postTest(): Response<testResponse> {
        return serviceImp.getTest()
    }
}
