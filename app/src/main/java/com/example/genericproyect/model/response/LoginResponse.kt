package com.example.genericproyect.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user") val user: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("direction") val direction: Direction

)

data class Direction(
    @SerializedName("street") val street: String,
    @SerializedName("height") val height: String,
    @SerializedName("postal_code") val cp: String

)
