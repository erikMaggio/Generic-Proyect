package com.example.login.model.dataSource

import com.example.login.model.response.*
import com.example.login.model.service.LoginService
import com.example.login.utils.CodesError.AUTH_ERROR
import com.example.login.utils.CodesError.CODE_401
import com.example.login.utils.CodesError.CODE_404
import com.example.login.utils.CodesError.CODE_500
import com.example.login.utils.CodesError.NOT_REGISTER
import com.example.login.utils.CodesError.SUCCESS_CREATE
import com.example.login.utils.CodesError.SUCCESS_LOGIN
import com.example.login.utils.CodesError.USER_REGISTER_ERROR
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.login.utils.Result
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class LoginDataSource {
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
        return when (call.code()) {
            200 -> {
                checkResponseLogin(call.body()!!.message)
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

    suspend fun postTest(): Response<testResponse> {
        return serviceImp.getTest()
    }

    suspend fun postRecoverPass(email: Recover): Response<RecoverResponse> {
        return serviceImp.postRecoverPass(email)

    }

    private fun checkResponseLogin(message: String): Result<LoginResponse> {
        return when (message) {

            "Usuario no registrado" -> {
                Result.success(message = NOT_REGISTER )
            }
            "Acceso no autorizado" -> {
                Result.success(message = AUTH_ERROR)
            }
            "" -> Result.success(message = SUCCESS_LOGIN)

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

            "" -> Result.success(message = SUCCESS_CREATE)

            else -> {
                Result.success(message = CODE_404)
            }
        }
    }
}

class TrustAllCerts : X509TrustManager {
    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}
