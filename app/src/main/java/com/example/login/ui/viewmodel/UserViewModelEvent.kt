package com.example.login.ui.viewmodel

import androidx.lifecycle.ViewModel

sealed class UserViewModelEvent:ViewModel() {

    data class RegisterSuccessful(val message:String) :UserViewModelEvent()

    data class RegisterError401(val message:String) :UserViewModelEvent()

    data class UserAlreadyRegister(val message: String) :UserViewModelEvent()

    //object Error:UserViewModelEvent()  // cuando no se pide valor x ponstructor
}