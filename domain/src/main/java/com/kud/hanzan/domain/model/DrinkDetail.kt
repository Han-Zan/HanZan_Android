package com.kud.hanzan.domain.model

import java.io.Serializable

data class DrinkDetail(
    val alcohol: Float,
    val bitter: Int,
    val body: Int,
    val category: Int,
    val detailedCategory: String,
    val id: Long,
    val img: String?,
    var isPrefer: Boolean,
    val name: String,
    val nation: String,
    val rating: Float,
    val sour: Int,
    val sparkle: Int,
    val sweet: Int,
    val tag: String
) : Serializable