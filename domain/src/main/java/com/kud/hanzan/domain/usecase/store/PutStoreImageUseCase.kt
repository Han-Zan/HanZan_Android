package com.kud.hanzan.domain.usecase.store

import com.kud.hanzan.domain.repository.StoreRepository
import javax.inject.Inject

class PutStoreImageUseCase @Inject constructor(
    private val repository: StoreRepository
) {
    operator fun invoke(imgLink: String, kakaoId: String) = repository.putStoreImage(imgLink, kakaoId)
}