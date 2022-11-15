package com.kud.hanzan.data.source.drink

import com.kud.hanzan.domain.model.DrinkDetail
import com.kud.hanzan.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkRepositoryImpl @Inject constructor(
    private val drinkRemoteDataSource: DrinkRemoteDataSource
): DrinkRepository {
    override fun getDrinkDetail(drinkIdx: Long, userIdx: Long)
    : Flow<DrinkDetail> = drinkRemoteDataSource.getDrinkDetail(drinkIdx, userIdx)
}