package com.example.login.model.dataSource

import com.example.login.model.response.*
import com.example.login.model.service.LoginService
import com.example.login.utils.CodesError.AUTH_ERROR
import com.example.login.utils.CodesError.CODE_401
import com.example.login.utils.CodesError.CODE_404
import com.example.login.utils.CodesError.CODE_500
import com.example.login.utils.CodesError.NOT_REGISTER
import com.example.login.utils.CodesError.SUCCESS_CREATE
import com.example.login.utils.CodesError.USER_REGISTER_ERROR
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.login.utils.Result
import okhttp3.OkHttpClient
import java.io.InputStream
import java.net.URLConnection
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class LoginDataSource {
    private val url = "http://nvfsolutions.com:8081/"

   // private val client = OkHttpClient.Builder()
//        .sslSocketFactory(
//            SSLContext.getInstance("SSL")
//                .apply {
//                    init(null, arrayOf(TrustAllCerts()), SecureRandom())
//                }
//                .socketFactory
//        )
//        .hostnameVerifier { _, _ -> true }
//        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
       // .client(client)
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
                checkResponse(call.body()!!.message)
            }
            401 -> {
                Result.error(message = CODE_401)
            }
            500 -> {
                Result.error(message = CODE_500)
            }
            404 -> {
                Result.error(message = "404")
                //crear object en utils de estas val
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

    private fun checkResponse(message: String): Result<SignUpResponse> {
        return when (message) {

            "Usuario ya registrado" -> {
                Result.success(message = USER_REGISTER_ERROR)
            }
            "Usuario no registrado" -> {
                Result.success(message = AUTH_ERROR)
            }
            "Acceso no autorizado" -> {
                Result.success(message = NOT_REGISTER)
            }
            "" -> Result.success(message = SUCCESS_CREATE)

            else -> {
                Result.success(message = CODE_404)
            }
        }
    }
}

//class TrustAllCerts : X509TrustManager {
//    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
//    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
//    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
//}
