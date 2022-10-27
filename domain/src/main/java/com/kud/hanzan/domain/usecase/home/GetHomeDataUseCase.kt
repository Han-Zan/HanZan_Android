package com.kud.hanzan.domain.usecase.home

import com.kud.hanzan.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(userIdx : Long) = repository.getHomeData(userIdx)
}