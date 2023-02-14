package com.example.login.ui.viewmodel

import androidx.lifecycle.ViewModel

sealed class UserViewModelEvent : ViewModel() {

    //data responseSuccess code:200
    data class UserSuccessful(val message: String) : UserViewModelEvent()

    //data response error code: 200
    data class UserAlreadyRegister(val message: String) : UserViewModelEvent()
    data class UserNotRegister(val message: String) : UserViewModelEvent()
    data class UserAuthError(val message: String) : UserViewModelEvent()

    //data response error code:401,404,500
    data class UserError404(val message: String) : UserViewModelEvent()
    data class RegisterError401(val message: String) : UserViewModelEvent()
    data class UserError500(val message: String) : UserViewModelEvent()

    //object Error:UserViewModelEvent()  // cuando no se pide valor x ponstructor
}