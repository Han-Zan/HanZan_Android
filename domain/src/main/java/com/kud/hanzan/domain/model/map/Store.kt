package com.kud.hanzan.domain.model.map

import java.io.Serializable

data class Store(
    val id: Long,
    val name: String,
    val category: String,
    val distance: Int,
    val detail: String,
    val address: String,
    val phone: String,
    val x: String,
    val y: String
) : Serializable