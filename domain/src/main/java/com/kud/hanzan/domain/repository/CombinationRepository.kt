package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.Drink
import kotlinx.coroutines.flow.Flow

interface CombinationRepository {
    fun getDrinkList(userIdx: Long) : Flow<List<Drink>>
}