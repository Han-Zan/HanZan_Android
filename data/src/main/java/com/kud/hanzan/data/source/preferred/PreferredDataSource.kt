package com.kud.hanzan.data.source.preferred

import com.kud.hanzan.data.entity.Drink
import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.data.entity.preferred.DrinkResult
import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import com.kud.hanzan.data.entity.preferred.PreferredDrinkDto
import kotlinx.coroutines.flow.Flow

interface PreferredDataSource {
    fun getPreferredComb(userId: Long) : Flow<CombResult>
    fun deletePreferredComb(userId: Long, combId: Long) : Flow<String>
    fun postPreferredComb(preferredCombDto: PreferredCombDto) : Flow<String>

    fun getPreferredAlcohol(userId: Long) : Flow<List<Drink>>
    fun deletePreferredAlcohol(userId: Long, drinkId: Long) : Flow<String>
    fun postPreferredAlcohol(preferredDrinkDto: PreferredDrinkDto) : Flow<DrinkResult>
}