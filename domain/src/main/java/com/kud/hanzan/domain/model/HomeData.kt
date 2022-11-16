package com.kud.hanzan.domain.model

data class HomeData(
    val lists: List<HomeComb>,
    val name: String,
    val sbti: String
)

data class HomeComb(
    val drink_img: String?,
    val drink_name: String,
    val food_img: String?,
    val food_name: String,
    val id: Long,
    var is_prefer: Boolean,
    val num_people: Int
)