package com.kud.hanzan.domain.model

data class Comb(
    val combIdx: Long,
    var prefer: Boolean,
    val rating: Float,
    val score: Int
)