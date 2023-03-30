package com.example.login.model.response

data class User(
    val email:String,
    val password:String,
    val device:String,
    val app_type:String
)
