package com.kud.hanzan.data.source.preferred

import com.kud.hanzan.data.entity.DrinkInfo
import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.data.entity.preferred.DrinkResult
import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import com.kud.hanzan.data.entity.preferred.PreferredDrinkDto
import com.kud.hanzan.domain.model.Combination
import com.kud.hanzan.domain.model.LikeDrink
import kotlinx.coroutines.flow.Flow

interface PreferredDataSource {
    fun getPreferredComb(userId: Long) : Flow<List<Combination>>
    fun deletePreferredComb(userId: Long, combId: Long) : Flow<String>
    fun postPreferredComb(preferredCombDto: PreferredCombDto) : Flow<String>

    fun getPreferredAlcohol(userId: Long) : Flow<List<LikeDrink>>
    fun deletePreferredAlcohol(userId: Long, drinkId: Long) : Flow<String>
    fun postPreferredAlcohol(preferredDrinkDto: PreferredDrinkDto) : Flow<DrinkResult>
}