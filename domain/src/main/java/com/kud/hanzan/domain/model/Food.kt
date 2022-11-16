package com.kud.hanzan.domain.model

import java.io.Serializable

data class Food(
    val bestComb: List<DrinkDetail>,
    val dessert: Int,
    val foodType: Int,
    val fruit: Int,
    val id: Long,
    val img: String?,
    val light: Int,
    val name: String,
    val oily: Int,
    val spicy: Int,
    val tag: String
) : Serializable