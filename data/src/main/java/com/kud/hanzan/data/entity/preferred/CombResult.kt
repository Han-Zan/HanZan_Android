package com.kud.hanzan.data.entity.preferred

class CombResult : ArrayList<CombResultItem>()

data class CombResultItem(
    val drinkname: String,
    val drinkimg: String?,
    val foodname: String,
    val foodimg: String?,
    val id: Long,
    val rating: Float,
)