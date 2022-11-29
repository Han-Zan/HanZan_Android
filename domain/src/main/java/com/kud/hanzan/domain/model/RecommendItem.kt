package com.kud.hanzan.domain.model

data class RecommendItem(
    val combId: Long,
    val drinkImg: String?,
    val drinkName: String,
    val foodImg: String?,
    val foodName: String,
    val highlyRec: Boolean,
    val combScore: Int
)