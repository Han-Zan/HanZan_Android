package com.kud.hanzan.data.source.combination

import com.kud.hanzan.data.entity.DrinkInfo
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CombinationRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : CombinationDataSource {
    override fun getDrinkList(): Flow<List<Drink>> = flow {
        emit(hanzanService.getDrinkList())
    }.flowOn(Dispatchers.IO)
}