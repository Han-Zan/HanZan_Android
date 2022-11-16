package com.kud.hanzan.domain.model

data class CombDrink(
    val alcohol: Float,
    val bitter: Int,
    val body: Int,
    val category: Int,
    val detailCategory: String,
    val id: Long,
    val img: String?,
    val name: String,
    val nation: String,
    val rating: Float,
    val sour: Int,
    val sparkle: Int,
    val sweet: Int,
    val tag: String
)