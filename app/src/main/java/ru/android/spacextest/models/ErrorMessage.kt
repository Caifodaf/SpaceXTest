package ru.android.spacextest.models

data class ErrorMessage (
    val type:Int = 0,
    val code: String,
    val message: String,
)