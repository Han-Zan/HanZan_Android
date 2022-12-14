package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.Combination
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.LikeDrink
import kotlinx.coroutines.flow.Flow

interface PreferredRepository {
    fun getPreferredComb(userId: Long) : Flow<List<Combination>>
    fun deletePreferredComb(userId: Long, combId: Long) : Flow<String>
    fun postPreferredComb(userId: Long, combId: Long) : Flow<String>

    fun getPreferredAlcohol(userId: Long) : Flow<List<Drink>>
    fun deletePreferredAlcohol(userId: Long, drinkId: Long) : Flow<String>
    fun postPreferredAlcohol(userId: Long, drinkId: Long) : Flow<String>
}