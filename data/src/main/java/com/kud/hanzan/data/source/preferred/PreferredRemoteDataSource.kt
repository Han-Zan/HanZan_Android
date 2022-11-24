package com.kud.hanzan.data.source.preferred

import com.kud.hanzan.data.entity.DrinkInfo
import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.data.entity.preferred.DrinkResult
import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import com.kud.hanzan.data.entity.preferred.PreferredDrinkDto
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.LikeDrink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PreferredRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : PreferredDataSource {
    override fun getPreferredComb(
        userId: Long
    ): Flow<CombResult> = flow {
        emit(hanzanService.getPreferredComb(userId))
    }.flowOn(Dispatchers.IO)

    override fun deletePreferredComb(
        userId: Long, combId: Long
    ): Flow<String> = flow{
        emit(hanzanService.deletePreferredComb(combId, userId))
    }.flowOn(Dispatchers.IO)

    override fun postPreferredComb(
        preferredCombDto: PreferredCombDto
    ): Flow<String> = flow {
        emit(hanzanService.postPreferredComb(preferredCombDto))
    }.flowOn(Dispatchers.IO)

    override fun getPreferredAlcohol(
        userId: Long
    ): Flow<List<LikeDrink>> = flow {
        emit(hanzanService.getPreferredAlcohol(userId))
    }.flowOn(Dispatchers.IO)

    override fun deletePreferredAlcohol(
        userId: Long, drinkId: Long
    ): Flow<String> = flow {
        emit(hanzanService.deletePreferredAlcohol(drinkId, userId))
    }.flowOn(Dispatchers.IO)

    override fun postPreferredAlcohol(
        preferredDrinkDto: PreferredDrinkDto
    ): Flow<DrinkResult> = flow {
        emit(hanzanService.postPreferredAlcohol(preferredDrinkDto))
    }.flowOn(Dispatchers.IO)
}