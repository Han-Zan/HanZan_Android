package com.kud.hanzan.domain.usecase.combination

import com.kud.hanzan.domain.repository.CombinationRepository
import javax.inject.Inject

class GetDrinkListUseCase @Inject constructor(
    private val repository: CombinationRepository
) {
    operator fun invoke() = repository.getDrinkList()
}