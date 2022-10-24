package com.kud.hanzan.domain.model

data class HomeCombination(
    val drinkname: String,
    val drinkimg: String?,
    val foodname: String,
    val foodimg: String?,
    val id: Long,
    var checked: Boolean
)
