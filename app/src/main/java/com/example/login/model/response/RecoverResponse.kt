package com.example.login.model.response

import com.google.gson.annotations.SerializedName

data class RecoverResponse(
    @SerializedName("error") val message :String
)
