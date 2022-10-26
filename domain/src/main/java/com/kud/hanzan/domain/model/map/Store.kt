package com.kud.hanzan.domain.model.map

data class Store(
    val id: Long,
    val name: String,
    val category: String,
    val distance: Int,
    val detail: String,
    val x: String,
    val y: String
)
