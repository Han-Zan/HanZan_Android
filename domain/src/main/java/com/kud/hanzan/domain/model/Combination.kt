package com.kud.hanzan.domain.model

data class Combination(
    val drinkname: String,
    val drinkimg: String?,
    val foodname: String,
    val foodimg: String?,
    val id: Long,
    val rating: Float,
    val detail: String?,
    var like: Boolean = true,
    var count: Int = 24,
)
