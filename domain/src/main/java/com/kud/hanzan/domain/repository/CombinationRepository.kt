package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.Comb
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface CombinationRepository {
    fun getDrinkList(userIdx: Long) : Flow<List<Drink>>
    suspend fun getAllFood() : List<Food>
    suspend fun recommandation(drinkName: String, foodName: String, userId: Long) : Comb
}