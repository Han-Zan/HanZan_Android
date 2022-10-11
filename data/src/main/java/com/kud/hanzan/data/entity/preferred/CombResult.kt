package com.kud.hanzan.data.entity.preferred

class CombResult : ArrayList<CombResultItem>()

data class CombResultItem(
    val drinkname: String,
    val foodname: String,
    val id: Long,
    val rating: Float
)