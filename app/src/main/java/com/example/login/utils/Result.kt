package com.example.login.utils

import java.io.Serializable

data class Result<out T>(val status: Status, private val data: T?, private val message: String?) :
    Serializable {

    companion object {

        fun <T> success(data: T? = null, message: String? = null): Result<T> {
            return Result(Status.SUCCESS, data, message)
        }

        fun <T> error(data: T? = null, message: String? = null): Result<T> {
            return Result(Status.ERROR, data, message)
        }
    }

    fun isSuccessful(): Boolean {
        return status == Status.SUCCESS
    }

}

enum class Status() {
    SUCCESS, ERROR
}
