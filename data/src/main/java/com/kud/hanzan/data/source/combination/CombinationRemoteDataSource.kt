package com.kud.hanzan.data.source.combination

import com.kud.hanzan.data.entity.DrinkInfo
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CombinationRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : CombinationDataSource {
    override fun getDrinkList(userIdx: Long): Flow<List<Drink>> = flow {
        emit(hanzanService.getDrinkList(userIdx).sortedBy { d -> d.name })
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllFood(): List<Food> {
        var foods: List<Food> = emptyList()
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.getAllFood()
            }.onSuccess {
                foods = it
            }.onFailure {

            }
        }
        return foods
    }

    override suspend fun recommandation(drinkName: String, foodName: String, userId: Long) : Comb {
        var combination: Comb = Comb(0, true, 0F, 0)
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.recommandation(drinkName, foodName, userId)
            }.onSuccess {
                combination = it
            }.onFailure {

            }
        }
        return combination
    }
}