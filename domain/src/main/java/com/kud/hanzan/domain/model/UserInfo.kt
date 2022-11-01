package com.kud.hanzan.domain.model

data class UserInfo(
    var username: String,
    var nickname: String,
    var userage: Int,
    var sbti: String,
    var profileimage: String?,
    var kakaoId: Long,
    var male: Boolean
)