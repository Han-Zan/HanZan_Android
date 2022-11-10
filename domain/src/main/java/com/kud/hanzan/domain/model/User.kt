package com.kud.hanzan.domain.model

data class User(
    var id: Int,
    var kakaoId: Long,
    var male: Boolean,
    var nickname: String,
    var profileimage: String,
    var sbti: String,
    var userage: Int,
    var username: String
)