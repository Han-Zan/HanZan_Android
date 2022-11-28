package com.kud.hanzan.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.HanZanApplication.Companion.spfManager
import com.kud.hanzan.domain.usecase.rating.PostRatingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val postRatingUseCase: PostRatingUseCase
) : ViewModel() {
    private var userIdx : Long = spfManager.getUserIdx()

    fun postRating(combIdx: Long, rating: Float){
        viewModelScope.launch {
            postRatingUseCase(combIdx, rating, userIdx)
                .catch {

                }
                .collect()
        }
    }
}