package com.kud.hanzan.data.source.ranking

import com.kud.hanzan.domain.model.CombinationDto
import com.kud.hanzan.domain.model.CombinationInfo
import com.kud.hanzan.domain.model.TempPreferedCombDto
import com.kud.hanzan.domain.repository.RankingRepository
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val rankingDataSource: RankingDataSource
): RankingRepository{
    override suspend fun getCombination(userId: Long, combId: Long) : CombinationInfo {
        return rankingDataSource.getCombination(userId, combId)
    }
    override suspend fun saveCombination(combinationDto: CombinationDto): String {
        return rankingDataSource.saveCombination(combinationDto)
    }
    override suspend fun listAll(userId: Long): List<CombinationInfo> {
        return rankingDataSource.listAll(userId)
    }
    override suspend fun deletePreferredComb(combId: Long, userId: Long) : String {
        return rankingDataSource.deletePreferredComb(combId, userId)
    }
    override suspend fun postPreferredComb(preferredCombDto: TempPreferedCombDto) : String {
        return rankingDataSource.postPreferredComb(preferredCombDto)
    }
}