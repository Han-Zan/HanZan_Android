package com.kud.hanzan.data.source.preferred

import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import kotlinx.coroutines.flow.Flow

interface PreferredDataSource {
    fun getPreferredComb(userId: Long) : Flow<CombResult>
    fun deletePreferredComb(userId: Long, combId: Long) : Flow<String>
    fun postPreferredComb(preferredCombDto: PreferredCombDto) : Flow<String>
}