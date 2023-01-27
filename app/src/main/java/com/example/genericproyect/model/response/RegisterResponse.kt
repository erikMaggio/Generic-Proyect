package com.example.genericproyect.model.response

data class RegisterResponse(
    val success: Boolean,
    val data: Data,
    val message: String
)

class Data