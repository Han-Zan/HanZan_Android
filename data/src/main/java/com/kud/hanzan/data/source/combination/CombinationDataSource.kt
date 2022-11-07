package com.kud.hanzan.data.source.combination

import com.kud.hanzan.data.entity.DrinkInfo
import com.kud.hanzan.domain.model.Drink
import kotlinx.coroutines.flow.Flow

interface CombinationDataSource {
    fun getDrinkList(userIdx: Long) : Flow<List<Drink>>
}