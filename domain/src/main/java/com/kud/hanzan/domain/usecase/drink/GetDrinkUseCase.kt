package com.kud.hanzan.domain.usecase.drink

import com.kud.hanzan.domain.repository.DrinkRepository
import javax.inject.Inject

class GetDrinkUseCase @Inject constructor(
    private val repository: DrinkRepository
){
    operator fun invoke(drinkIdx: Long, userIdx: Long) = repository.getDrinkDetail(drinkIdx, userIdx)
}