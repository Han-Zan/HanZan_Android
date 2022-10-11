package com.kud.hanzan.domain.usecase.kakao

import android.util.Log
import com.kud.hanzan.domain.repository.KakaoRepository
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetKeywordPlaceUseCase @Inject constructor(
    private val repository: KakaoRepository
){
    operator fun invoke(keyword: String) = repository.getKeywordPlace(keyword)
}