package com.kud.hanzan.domain.usecase.preferred

import com.kud.hanzan.domain.repository.PreferredRepository
import javax.inject.Inject

class GetPreferredCombUseCase @Inject constructor(
    private val repository: PreferredRepository
){
    operator fun invoke(userIdx: Long) = repository.getPreferredComb(userIdx)
}