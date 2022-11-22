package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.CombinationDto
import com.kud.hanzan.domain.model.CombinationInfo
import com.kud.hanzan.domain.model.TempPreferedCombDto

interface RankingRepository {
    suspend fun getCombination(userId: Long, combId: Long) : CombinationInfo
    suspend fun saveCombination(combinationDto: CombinationDto) : String
    suspend fun listAll(userId: Long) : List<CombinationInfo>
    suspend fun deletePreferredComb(combId: Long, userId: Long) : String
    suspend fun postPreferredComb(preferredCombDto: TempPreferedCombDto) : String
}