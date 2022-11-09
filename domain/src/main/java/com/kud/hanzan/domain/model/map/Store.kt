package com.kud.hanzan.domain.model.map

import java.io.Serializable

data class Store(
    val id: String,
    val name: String,
    val category: String,
    val detail: String,
    val address: String,
    val phone: String,
    val x: String,
    val y: String
) : Serializable