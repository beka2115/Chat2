package com.example.chat2.model
import java.io.Serializable

data class ChatUser(
    var uid: String? = null,
    var phone: String? = null,
    var userName: String? = null,
):Serializable
