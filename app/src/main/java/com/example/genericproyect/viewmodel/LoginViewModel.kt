package com.example.genericproyect.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val liveCheckLoginData = MutableLiveData<Boolean>()

    fun checkState(login: String) {
        if (login.isNotEmpty()
            && !login.contains(" ")
            && !login.contains("-")
            && !login.contains("_")
            && !login.contains(",")
            && !login.contains(":")
            && !login.contains(";")
        ) {
            liveCheckLoginData.postValue(true)
        } else {
            liveCheckLoginData.postValue(false)
        }
    }
}