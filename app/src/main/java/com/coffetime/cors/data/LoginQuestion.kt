package com.coffetime.cors.data

class LoginQuestion(
    val title: String,
    val imagePath: Int,
    val questionsList: List<String>,
    var expanded: Boolean = false,
    var finalQuestion: Boolean = false
)