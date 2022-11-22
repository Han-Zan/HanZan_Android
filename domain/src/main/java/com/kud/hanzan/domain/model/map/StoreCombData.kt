package com.kud.hanzan.domain.model.map

data class StoreCombData(
    val combinationList: List<StoreComb>,
    val idx: Int,
    val imgLink: String?,
    val kakaoId: String,
    val storeName: String
)

data class StoreComb(
    val combScore: Int,
    val drinkCategory: Int,
    val drinkimg: String?,
    val drinkname: String,
    val foodCategory: Int,
    val foodimg: String?,
    val foodname: String,
    val id: Long,
    val isPrefer: Boolean,
    val pnum: Int,
    val rating: Float
)