package com.example.login.utils

data class Action(
    val type: Type,
    val color: Int,
    val label: String,
    val onClick: () -> Unit
)