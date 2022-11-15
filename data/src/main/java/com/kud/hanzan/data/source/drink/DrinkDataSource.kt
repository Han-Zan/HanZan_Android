package com.kud.hanzan.data.source.drink

import com.kud.hanzan.domain.model.DrinkDetail
import kotlinx.coroutines.flow.Flow

interface DrinkDataSource {
    fun getDrinkDetail(drinkIdx: Long, userIdx: Long) : Flow<DrinkDetail>
}