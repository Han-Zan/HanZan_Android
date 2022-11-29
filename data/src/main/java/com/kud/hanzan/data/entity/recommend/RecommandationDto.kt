package com.kud.hanzan.data.entity.recommend

data class RecommandationDto(
    val drinkNames: List<String>,
    val foodNames: List<String>,
    val userIdx: Long
)