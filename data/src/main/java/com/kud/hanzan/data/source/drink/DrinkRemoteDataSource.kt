package com.kud.hanzan.data.source.drink

import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.DrinkDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DrinkRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : DrinkDataSource{
    override fun getDrinkDetail(
        drinkIdx: Long, userIdx: Long)
    : Flow<DrinkDetail> = flow {
        emit(hanzanService.getDrinkDetail(drinkIdx, userIdx))
    }.flowOn(Dispatchers.IO)
}