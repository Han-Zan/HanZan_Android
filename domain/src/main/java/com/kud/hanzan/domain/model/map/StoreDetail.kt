package com.kud.hanzan.domain.model.map

data class StoreDetail(
    val id: Long,
    val name: String,
    val address: String?,
    val phone: String?,
    val rating: Double,
    val imgList: List<String>,
    val combList: List<String>
)
