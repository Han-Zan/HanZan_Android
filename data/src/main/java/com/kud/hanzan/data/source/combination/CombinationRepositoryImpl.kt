package com.kud.hanzan.data.source.combination

import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.repository.CombinationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CombinationRepositoryImpl @Inject constructor(
    private val combinationRemoteDataSource: CombinationRemoteDataSource
) : CombinationRepository{
    override fun getDrinkList()
    : Flow<List<Drink>> = combinationRemoteDataSource.getDrinkList().map {
        it.map { drink -> Drink(drink.id, drink.name, drink.category, drink.rating, drink.img, drink.tag) }
    }
}