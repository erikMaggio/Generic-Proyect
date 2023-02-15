package com.example.login.ui.viewmodel

import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login.model.repository.LoginRepository
import com.example.login.model.response.*
import com.example.login.utils.AlertErrorField
import com.example.login.utils.CodesError.AUTH_ERROR
import com.example.login.utils.CodesError.CODE_401
import com.example.login.utils.CodesError.CODE_404
import com.example.login.utils.CodesError.CODE_500
import com.example.login.utils.CodesError.NOT_REGISTER
import com.example.login.utils.CodesError.SUCCESS_CREATE
import com.example.login.utils.CodesError.SUCCESS_LOGIN
import com.example.login.utils.CodesError.USER_REGISTER_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val data = MutableLiveData<UserViewModelEvent>()
    val liveEmailData = MutableLiveData<Boolean>()
    val liveCheckUserData = MutableLiveData<Boolean>()
    val liveAlertData = MutableLiveData<AlertErrorField>()

    private val loginRepository = LoginRepository()

    //validation field login
    fun checkStateLogin(email: String, password: String) {
        if (verifyEmail(email) && verifyPassword(password)) {
            liveCheckUserData.postValue(true)
        } else {
            liveCheckUserData.postValue(false)
        }
    }

    //validation field create account
    fun checkStateCreate(user: String, email: String, password: String, confirmPassword: String) {
        if (verifyUser(user) && verifyEmail(email) && verifyPassword(password)
            && verifyConfirmPassword(confirmPassword, password)
        ) {
            liveCheckUserData.postValue(true)
        } else {
            liveCheckUserData.postValue(false)
        }
    }

    fun checkEmailRecover(email: String) {
        liveEmailData.postValue(verifyEmail(email))
    }

    //api login
    fun postLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myUser = User(email, password)
            val call = loginRepository.postLogin(myUser)
            if (call.isSuccessful()) {
                when (call.message) {

                    SUCCESS_LOGIN -> {
                        data.postValue(UserViewModelEvent.UserSuccessful(call.message))
                    }
                    AUTH_ERROR -> {
                        data.postValue(UserViewModelEvent.UserAuthError(call.message))
                    }

                    NOT_REGISTER -> {
                        data.postValue(UserViewModelEvent.UserNotRegister(call.message))
                    }
                }
            } else {
                when (call.message) {
                    CODE_500 -> {
                        data.postValue(UserViewModelEvent.UserError500(call.message))
                    }
                    CODE_401 -> {
                        data.postValue(UserViewModelEvent.RegisterError401(call.message))
                    }
                    CODE_404 -> {
                        data.postValue(UserViewModelEvent.UserError404(call.message))
                    }
                }
            }
        }
    }

    //api sign up
    fun postSignUp(name: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myUser = Register(name, email, password)
            val call = loginRepository.postSignUp(myUser)
            if (call.isSuccessful()) {
                when (call.message) {
                    SUCCESS_CREATE -> {
                        data.postValue(UserViewModelEvent.UserSuccessful(call.message))
                    }
                    USER_REGISTER_ERROR -> {
                        data.postValue(UserViewModelEvent.UserAlreadyRegister(call.message))
                    }
                    NOT_REGISTER -> {
                        data.postValue(UserViewModelEvent.UserNotRegister(call.message))
                    }

                }
            } else {
                when (call.message) {
                    CODE_500 -> {
                        data.postValue(UserViewModelEvent.UserError500(call.message))
                    }
                    CODE_401 -> {
                        data.postValue(UserViewModelEvent.RegisterError401(call.message))
                    }
                    CODE_404 -> {
                        data.postValue(UserViewModelEvent.UserError404(call.message))
                    }
                }
            }
        }
    }

    //api forgot pass
    fun postRecoverPass(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myUser = Recover(email)
            val call = loginRepository.postRecoverPass(myUser)
            if (call.isSuccessful()) {

                when (call.message) {
                    SUCCESS_CREATE -> {
                        data.postValue(UserViewModelEvent.UserSuccessful(call.message))
                    }
                    NOT_REGISTER -> {
                        data.postValue(UserViewModelEvent.UserNotRegister(call.message))
                    }
                }
            } else {
                when (call.message) {
                    CODE_500 -> {
                        data.postValue(UserViewModelEvent.UserError500(call.message))
                    }
                    CODE_401 -> {
                        data.postValue(UserViewModelEvent.RegisterError401(call.message))
                    }
                    CODE_404 -> {
                        data.postValue(UserViewModelEvent.UserError404(call.message))
                    }
                }
            }
        }
    }


    //verification de campos
    private fun verifyUser(user: String): Boolean {
        return if (user.matches("[a-zA-Z0-9]+".toRegex())
            && (user.length >= 3)
            && user.isNotEmpty()
        ) {
            liveAlertData.postValue(AlertErrorField.SUCCESS)
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
            liveAlertData.postValue(AlertErrorField.SUCCESS)
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
            liveAlertData.postValue(AlertErrorField.SUCCESS)
            true
        } else {
            liveAlertData.postValue(AlertErrorField.ERROR_PASSWORD)
            false
        }
    }

    private fun verifyConfirmPassword(pass1: String, pass2: String): Boolean {
        return if (pass1 == pass2) {
            liveAlertData.postValue(AlertErrorField.SUCCESS)
            true
        } else {
            liveAlertData.postValue(AlertErrorField.ERROR_CONFIRM_PASS)
            false
        }
    }
}