package com.kud.hanzan.domain.usecase

import com.kud.hanzan.domain.repository.KakaoRepository
import com.kud.hanzan.domain.utils.error.RemoteErrorEmitter
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetKeywordPlaceUseCase @Inject constructor(
    private val repository: KakaoRepository
){
    operator fun invoke(emitter: RemoteErrorEmitter, keyword: String) = repository.getKeywordPlace(emitter, keyword)
}