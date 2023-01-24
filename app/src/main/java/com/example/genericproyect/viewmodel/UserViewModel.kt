package com.example.genericproyect.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.genericproyect.model.repository.LoginRepository
import com.example.genericproyect.model.response.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val liveCheckUserData = MutableLiveData<Boolean>()
    val liveUserData = MutableLiveData<LoginResponse>()
    private val loginRepository = LoginRepository()

    fun checkState(login: String, password: String) {
        if (login.isNotEmpty()
            && password.isNotEmpty()
            && verifyPassword(password)
            && !login.contains(" ")
            && !login.contains("-")
            && !login.contains("_")
            && !login.contains(",")
            && !login.contains(":")
            && !login.contains(";")
            && !password.contains(" ")
            && !password.contains("-")
            && !password.contains("_")
            && !password.contains(",")
            && !password.contains(":")
            && !password.contains(";")
            && login.length >= 3 && password.length >= 3
        ) {
            liveCheckUserData.postValue(true)
        } else {
            liveCheckUserData.postValue(false)
        }
    }

    fun checkStateCreate(user: String, email: String, password: String) {
        if (user.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
            && verifyUser(user) && verifyPassword(password)
            && !user.contains(" ") && !email.contains(" ") && !password.contains(" ")
            && !user.contains("-") && !email.contains("-") && !password.contains("-")
            && !user.contains("_") && !email.contains("_") && !password.contains("_")
            && !user.contains(",") && !email.contains(",") && !password.contains(",")
            && !user.contains(":") && !email.contains(":") && !password.contains(":")
            && !user.contains(";") && !email.contains(";") && !password.contains(";")
            && user.length >= 3 && email.length >= 3 && password.length >= 3
        ) {
            liveCheckUserData.postValue(true)
        } else {
            liveCheckUserData.postValue(false)
        }
    }

    private fun verifyUser(user: String): Boolean {
        return user.matches("[a-zA-Z0-9]+".toRegex())
    }

    private fun verifyPassword(password: String): Boolean {
        return password.matches("[a-zA-Z0-9]+".toRegex())
    }


    //consumo de api rest

    fun getLogin(email:String,password:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = loginRepository.getLogin(email,password)
            if (call.isSuccessful)
                liveUserData.postValue(call.body())
        }
    }
}