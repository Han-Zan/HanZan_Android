package com.kud.hanzan.domain.usecase.preferred

import com.kud.hanzan.domain.repository.PreferredRepository
import javax.inject.Inject

class PostPreferredDrinkUseCase @Inject constructor(
    private val repository: PreferredRepository
){
    operator fun invoke(userId: Long, drinkId: Long) = repository.postPreferredAlcohol(userId, drinkId)
}