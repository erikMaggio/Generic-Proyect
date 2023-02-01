package com.example.login.ui.viewmodel

import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login.model.repository.LoginRepository
import com.example.login.utils.AlertErrorField
import com.example.login.model.response.LoginResponse
import com.example.login.model.response.Register
import com.example.login.model.response.SignUpResponse
import com.example.login.model.response.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val liveCheckUserData = MutableLiveData<Boolean>()
    val liveAlertData = MutableLiveData<AlertErrorField>()
    val liveUserData = MutableLiveData<LoginResponse>()
    val liveNewAccountData = MutableLiveData<SignUpResponse>()
    private val loginRepository = LoginRepository()

    //validation field login
    fun checkStateLogin(email: String, password: String) {

        liveCheckUserData.postValue(verifyEmail(email) && verifyPassword(password))
    }

    //validation field create account
    fun checkStateCreate(user: String, email: String, password: String, confirmPassword: String) {
        if (verifyUser(user) && verifyEmail(email) && verifyPassword(password)
            && verifyConfirmPassword(confirmPassword, password)
        ) {
            liveCheckUserData.postValue(true)
            liveAlertData.postValue(AlertErrorField.SUCCESS)
        } else {
            liveCheckUserData.postValue(false)
        }
    }

    //api login
    fun postLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myUser = User(email, password)
            val call = loginRepository.postLogin(myUser)
            if (call.isSuccessful)
                liveUserData.postValue(call.body())
        }
    }

    //api sign up
    fun postSignUp(name: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myUser = Register(name, email, password)
            val call = loginRepository.postSignUp(myUser)
            if (call.isSuccessful)
                liveNewAccountData.postValue(call.body())
        }
    }


    //verificaciones de campos
    private fun verifyUser(user: String): Boolean {
        return if (user.matches("[a-zA-Z0-9]+".toRegex())
            && (user.length >= 3)
            && user.isNotEmpty()
        ) {
            true
        } else {
            liveAlertData.postValue(AlertErrorField.ERROR_USER)
            false
        }
    }

    private fun verifyEmail(email: String): Boolean {
        return if (PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
            && email.length >= 3
            && email.isNotEmpty()
        ) {
            true
        } else {
            liveAlertData.postValue(AlertErrorField.ERROR_EMAIL)
            false
        }
    }

    private fun verifyPassword(password: String): Boolean {
        return if (password.matches("[a-zA-Z0-9]+".toRegex())
            && password.length >= 3
            && password.isNotEmpty()
        ) {
            true
        } else {
            liveAlertData.postValue(AlertErrorField.ERROR_PASSWORD)
            false
        }
    }

    private fun verifyConfirmPassword(pass1: String, pass2: String): Boolean {
        return if (pass1 == pass2) {
            true
        } else {
            liveAlertData.postValue(AlertErrorField.ERROR_CONFIRM_PASS)
            false
        }
    }

}