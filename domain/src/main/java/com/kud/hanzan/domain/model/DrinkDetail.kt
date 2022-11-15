package com.kud.hanzan.domain.model

data class DrinkDetail(
    val alcohol: Float,
    val aroma: String,
    val bitter: Int,
    val body: Int,
    val category: Int,
    val detailedCategory: String,
    val finish: String,
    val id: Long,
    val img: String?,
    val isPrefer: Boolean,
    val name: String,
    val nation: String,
    val rating: Float,
    val sour: Int,
    val sparkle: Int,
    val sweet: Int,
    val tag: String,
    val taste: String
)