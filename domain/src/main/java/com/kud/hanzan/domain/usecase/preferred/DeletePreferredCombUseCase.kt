package com.kud.hanzan.domain.usecase.preferred

import com.kud.hanzan.domain.repository.PreferredRepository
import javax.inject.Inject

class DeletePreferredCombUseCase @Inject constructor(
    private val repository: PreferredRepository
) {
    operator fun invoke(userId: Long, combId: Long) = repository.deletePreferredComb(userId, combId)
}