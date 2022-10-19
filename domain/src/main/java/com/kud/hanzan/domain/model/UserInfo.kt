package com.kud.hanzan.domain.model

data class UserInfo(
    var male: Boolean,
    var nickname: String,
    var profileimage: String?,
    var sbti: String,
    var token: String,
    var userage: Int,
    var username: String
)