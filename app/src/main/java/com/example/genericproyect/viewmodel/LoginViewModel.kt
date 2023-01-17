package com.example.genericproyect.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val liveCheckLoginData = MutableLiveData<Boolean>()

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
            && login.length >= 3
        ) {
            liveCheckLoginData.postValue(true)
        } else {
            liveCheckLoginData.postValue(false)
        }
    }

    fun verifyPassword(password: String): Boolean {
        return password.matches("[a-zA-Z0-9]+".toRegex())
    }
}