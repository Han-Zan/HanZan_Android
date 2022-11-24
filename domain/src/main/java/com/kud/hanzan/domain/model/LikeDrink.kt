package com.kud.hanzan.domain.model

data class LikeDrink(
    val category: Int,
    val drinkId: Long,
    val idx: Long,
    val img: String?,
    val name: String,
    val rating: Float,
    val tag: String
)