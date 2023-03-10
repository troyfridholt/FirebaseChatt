package com.example.firebasechatt

data class Message(
    val userId: String = "",
    val messageText: String = "",
    val timestamp: Long = 0
)
