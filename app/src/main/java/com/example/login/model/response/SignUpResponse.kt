package com.example.login.model.response

import com.google.gson.annotations.SerializedName


data class SignUpResponse(
    @SerializedName("error") val message: String
)

