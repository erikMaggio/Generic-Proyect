package com.example.login.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String,
    val name: String,
    val avatar: String,
    val permissions: Permissions,
    @SerializedName("error") val message: String,
    @SerializedName("error_id") val messageId: Int
)

data class Permissions(
    val rol: String,
    val allows: Allow
)

data class Allow(
    val admin: Boolean,
    val payment: Boolean
)
