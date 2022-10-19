package com.kud.hanzan.data.entity

data class Drink(
    val aroma: String,
    val bitter: Int,
    val category: Int,
    val finish: String,
    val id: Long,
    val img: String?,
    val name: String,
    val rating: Float,
    val salty: Int,
    val sour: Int,
    val sparkle: Int,
    val sweet: Int,
    val tag: String,
    val taste: String
)