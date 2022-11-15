package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.DrinkDetail
import kotlinx.coroutines.flow.Flow

interface DrinkRepository {
    fun getDrinkDetail(drinkIdx: Long, userIdx: Long) : Flow<DrinkDetail>
}