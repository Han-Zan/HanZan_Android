package com.kud.hanzan.domain.model

data class Drink(
    val id: Long,
    val name: String,
    val category: Int,
    val rating: Float,
    val imgRes: String?,
    val tag: String,
    var like: Boolean = true,
)
