package com.kud.hanzan.domain.model

data class Alcohol(
    val name: String,
    val type: String,
    val typeNum: Int,
    val rating: Double,
    val imgRes: Int?,
    val tag: String
)
