package com.kud.hanzan.data.source.preferred

import com.kud.hanzan.domain.model.Alcohol
import com.kud.hanzan.domain.model.Combination
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
}