package com.example.login.model.dataSource

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.login.model.response.*
import com.example.login.model.service.LoginService
import com.example.login.utils.AppDataBaseRoom
import com.example.login.utils.CodesError.AUTH_ERROR
import com.example.login.utils.CodesError.CODE_401
import com.example.login.utils.CodesError.CODE_404
import com.example.login.utils.CodesError.CODE_500
import com.example.login.utils.CodesError.NOT_REGISTER
import com.example.login.utils.CodesError.RECOVER_PASS
import com.example.login.utils.CodesError.SUCCESS_CREATE
import com.example.login.utils.CodesError.SUCCESS_LOGIN
import com.example.login.utils.CodesError.USER_REGISTER_ERROR
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.login.utils.Result
import okhttp3.OkHttpClient
import java.security.SecureRandom
import com.example.login.model.room.entity.UserEntity
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class LoginDataSource(application: Application) {

    private val userDao = AppDataBaseRoom.getInstance(application).userDao()
    private val url = "https://nvfsolutions.com:8443/"

    private val client = OkHttpClient.Builder()
        .sslSocketFactory(
            SSLContext.getInstance("SSL")
                .apply {
                    init(null, arrayOf(TrustAllCerts()), SecureRandom())
                }
                .socketFactory
        )
        .hostnameVerifier { _, _ -> true }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serviceImp = retrofit.create(LoginService::class.java)

    suspend fun postLogin(user: User): Result<LoginResponse> {
        val call: Response<LoginResponse> = serviceImp.postLogin(user)
        Log.i("token",call.toString())
        Log.i("token",call.body().toString())
        Log.i("token",call.message().toString())

        return when (call.code()) {
            200 -> {
                checkResponseLogin(call.body()?.message,call.body()!!.token)
            }
            401 -> {
                Result.error(message = CODE_401)
            }
            500 -> {
                Result.error(message = CODE_500)
            }
            404 -> {
                Result.error(message = CODE_404)
            }

            else -> {
                Result.error(message = "0")
            }
        }
    }

    suspend fun postSignUp(register: Register): Result<SignUpResponse> {
        val call: Response<SignUpResponse> = serviceImp.postSignUp(register)

        return when (call.code()) {
            200 -> {
                checkResponseSignUp(call.body()!!.message)
            }
            401 -> {
                Result.error(message = CODE_401)
            }
            500 -> {
                Result.error(message = CODE_500)
            }
            404 -> {
                Result.error(message = CODE_404)
            }

            else -> {
                Result.error(message = "0")
            }
        }
    }

    suspend fun postRecoverPass(email: Recover): Result<RecoverResponse> {
        val call: Response<RecoverResponse> = serviceImp.postRecoverPass(email)

        return when (call.code()) {
            200 -> {
                checkResponseRecover(call.body()!!.message)
            }
            401 -> {
                Result.error(message = CODE_401)
            }
            500 -> {
                Result.error(message = CODE_500)
            }
            404 -> {
                Result.error(message = CODE_404)
            }

            else -> {
                Result.error(message = "0")
            }
        }
    }

    private fun checkResponseLogin(message: String?,token: String): Result<LoginResponse> {
        return when (message) {

            "Usuario no registrado" -> {
                Result.success(message = NOT_REGISTER)
            }
            "Acceso no autorizado" -> {
                Result.success(message = AUTH_ERROR)
            }
             null -> Result.success(message = token)

            else -> {
                Result.success(message = CODE_404)
            }
        }
    }

    private fun checkResponseSignUp(message: String): Result<SignUpResponse> {
        return when (message) {

            "Usuario ya registrado" -> {
                Result.success(message = USER_REGISTER_ERROR)
            }
            "Usuario no registrado" -> {
                Result.success(message = NOT_REGISTER)
            }
            "" -> {
                Result.success(message = SUCCESS_CREATE)
            }
            else -> {
                Result.success(message = CODE_404)
            }
        }
    }

    private fun checkResponseRecover(message: String): Result<RecoverResponse> {
        return when (message) {
            "" -> {
                Result.success(message = RECOVER_PASS)
            }
            "Usuario no registrado" -> {
                Result.success(message = NOT_REGISTER)
            }
            else -> {
                Result.success(message = CODE_404)
            }
        }
    }

    suspend fun getUser(){
        userDao.getAllUser()
    }

    suspend fun saveTokenUser(token : String){
        userDao.insertUser(UserEntity(token = token))
    }

}

class TrustAllCerts : X509TrustManager {
    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}
