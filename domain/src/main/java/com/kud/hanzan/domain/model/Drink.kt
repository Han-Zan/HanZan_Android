package com.kud.hanzan.domain.model

data class Drink(
    val id: Long,
    val name: String,
    val category: Int,
    val rating: Float,
    val img: String?,
    val tag: String,
    var isPrefer: Boolean
)
