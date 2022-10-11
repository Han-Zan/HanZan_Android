package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.Combination
import kotlinx.coroutines.flow.Flow

interface PreferredRepository {
    fun getPreferredComb(userId: Long) : Flow<List<Combination>>
}