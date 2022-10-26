package com.kud.hanzan.data.source.combination

import com.kud.hanzan.data.entity.DrinkInfo
import kotlinx.coroutines.flow.Flow

interface CombinationDataSource {
    fun getDrinkList() : Flow<List<DrinkInfo>>
}