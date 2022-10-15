package com.kud.hanzan.domain.model

data class Combination(
    val drinkname: String,
    val foodname: String,
    val id: Long,
    val rating: Float,
    val detail: String?
)
