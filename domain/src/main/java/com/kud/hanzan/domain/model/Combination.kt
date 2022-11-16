package com.kud.hanzan.domain.model

data class Combination(
    val combScore: Int?,
    val drinkCategory: Int,
    val drinkimg: String?,
    val drinkname: String,
    val foodCategory: Int,
    val foodimg: String?,
    val foodname: String,
    val id: Long,
    var pnum: Int,
    val rating: Int,
    var like: Boolean = true
)