package com.katiacompany.clonetelegram.models

data class CommonModel(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var fullname: String = "",
    var phone: String = "",
    var status: String = "",
    var photoUrl: String = "empty"
)