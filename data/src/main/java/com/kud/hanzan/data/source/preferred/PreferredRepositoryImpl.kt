package com.kud.hanzan.data.source.preferred

import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import com.kud.hanzan.data.entity.preferred.PreferredDrinkDto
import com.kud.hanzan.domain.model.Combination
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.repository.PreferredRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferredRepositoryImpl @Inject constructor(
    private val preferredRemoteDataSource : PreferredRemoteDataSource
) : PreferredRepository {
    override fun getPreferredComb(
        userId: Long
    ): Flow<List<Combination>> = preferredRemoteDataSource.getPreferredComb(userId).map {
        it.map { comb -> Combination(comb.drinkname, comb.drinkimg, comb.foodname, comb.foodimg, comb.id, comb.rating, null) }
    }

    override fun deletePreferredComb(
        userId: Long, combId: Long
    ): Flow<String> = preferredRemoteDataSource.deletePreferredComb(userId, combId)

    override fun postPreferredComb(
        userId: Long, combId: Long
    ): Flow<String> = preferredRemoteDataSource.postPreferredComb(PreferredCombDto(combId, userId))


    override fun getPreferredAlcohol(
        userId: Long
    ): Flow<List<Drink>> = preferredRemoteDataSource.getPreferredAlcohol(userId).map {
        // Todo : DrinkInfo, Drink 클래스 차이 구분하여 적용
        it.reversed().map { drink -> Drink(drink.id, drink.name, drink.category, drink.rating, drink.img, drink.tag, true) }
    }

    override fun deletePreferredAlcohol(
        userId: Long, drinkId: Long
    ) : Flow<String> = preferredRemoteDataSource.deletePreferredAlcohol(userId, drinkId)

    override fun postPreferredAlcohol(
        userId: Long, drinkId: Long
    ): Flow<String> = preferredRemoteDataSource.postPreferredAlcohol(PreferredDrinkDto(drinkId, userId)).map {
        it.createdAt
    }
}