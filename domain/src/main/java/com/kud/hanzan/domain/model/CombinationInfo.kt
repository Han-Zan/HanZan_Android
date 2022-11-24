package com.kud.hanzan.domain.model

data class CombinationInfo(
    var combScore: Int,
    var drinkCategory: Int,
    var drinkimg: String?,
    var drinkname: String,
    var foodCategory: Int,
    var foodimg: String?,
    var foodname: String,
    var id: Long,
    var isPrefer: Boolean,
    var pnum: Int,
    var rating: Float
)