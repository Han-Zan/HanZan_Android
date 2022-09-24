package com.kud.hanzan.ui.sbti

import android.os.Bundle
import android.view.View
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentSbtiBinding
import com.kud.hanzan.utils.base.BaseFragment

class SbtiFragment() : BaseFragment<FragmentSbtiBinding>(R.layout.fragment_sbti){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener(){
        // 각 문제들의 답 ([0]은 더미)
        val _answer = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        with(binding){
            sbtiAnswer11CB.setOnClickListener {
                _answer[1] = 1
                sbtiAnswer12CB.isChecked = false
            }
            sbtiAnswer12CB.setOnClickListener {
                _answer[1] = 2
                sbtiAnswer11CB.isChecked = false
            }

            sbtiAnswer21CB.setOnClickListener {
                _answer[2] = 1
                sbtiAnswer22CB.isChecked = false
            }
            sbtiAnswer22CB.setOnClickListener {
                _answer[2] = 2
                sbtiAnswer21CB.isChecked = false
            }

            sbtiAnswer31CB.setOnClickListener {
                _answer[3] = 1
                sbtiAnswer32CB.isChecked = false
            }
            sbtiAnswer32CB.setOnClickListener {
                _answer[3] = 2
                sbtiAnswer31CB.isChecked = false
            }

            sbtiAnswer41CB.setOnClickListener {
                _answer[4] = 1
                sbtiAnswer42CB.isChecked = false
            }
            sbtiAnswer42CB.setOnClickListener {
                _answer[4] = 2
                sbtiAnswer41CB.isChecked = false
            }

            sbtiAnswer51CB.setOnClickListener {
                _answer[5] = 1
                sbtiAnswer52CB.isChecked = false
            }
            sbtiAnswer52CB.setOnClickListener {
                _answer[5] = 2
                sbtiAnswer51CB.isChecked = false
            }

            sbtiAnswer61CB.setOnClickListener {
                _answer[6] = 1
                sbtiAnswer62CB.isChecked = false
                sbtiAnswer63CB.isChecked = false
                sbtiAnswer64CB.isChecked = false
                sbtiAnswer65CB.isChecked = false
            }
            sbtiAnswer62CB.setOnClickListener {
                _answer[6] = 2
                sbtiAnswer61CB.isChecked = false
                sbtiAnswer63CB.isChecked = false
                sbtiAnswer64CB.isChecked = false
                sbtiAnswer65CB.isChecked = false
            }
            sbtiAnswer63CB.setOnClickListener {
                _answer[6] = 3
                sbtiAnswer61CB.isChecked = false
                sbtiAnswer62CB.isChecked = false
                sbtiAnswer64CB.isChecked = false
                sbtiAnswer65CB.isChecked = false
            }
            sbtiAnswer64CB.setOnClickListener {
                _answer[6] = 4
                sbtiAnswer61CB.isChecked = false
                sbtiAnswer62CB.isChecked = false
                sbtiAnswer63CB.isChecked = false
                sbtiAnswer65CB.isChecked = false
            }
            sbtiAnswer65CB.setOnClickListener {
                _answer[6] = 5
                sbtiAnswer61CB.isChecked = false
                sbtiAnswer62CB.isChecked = false
                sbtiAnswer63CB.isChecked = false
                sbtiAnswer64CB.isChecked = false
            }

            sbtiAnswer71CB.setOnClickListener {
                _answer[7] = 1
                sbtiAnswer72CB.isChecked = false
                sbtiAnswer73CB.isChecked = false
                sbtiAnswer74CB.isChecked = false
                sbtiAnswer75CB.isChecked = false
            }
            sbtiAnswer72CB.setOnClickListener {
                _answer[7] = 2
                sbtiAnswer71CB.isChecked = false
                sbtiAnswer73CB.isChecked = false
                sbtiAnswer74CB.isChecked = false
                sbtiAnswer75CB.isChecked = false
            }
            sbtiAnswer73CB.setOnClickListener {
                _answer[7] = 3
                sbtiAnswer71CB.isChecked = false
                sbtiAnswer72CB.isChecked = false
                sbtiAnswer74CB.isChecked = false
                sbtiAnswer75CB.isChecked = false
            }
            sbtiAnswer74CB.setOnClickListener {
                _answer[7] = 4
                sbtiAnswer71CB.isChecked = false
                sbtiAnswer72CB.isChecked = false
                sbtiAnswer73CB.isChecked = false
                sbtiAnswer75CB.isChecked = false
            }
            sbtiAnswer75CB.setOnClickListener {
                _answer[7] = 5
                sbtiAnswer71CB.isChecked = false
                sbtiAnswer72CB.isChecked = false
                sbtiAnswer73CB.isChecked = false
                sbtiAnswer74CB.isChecked = false
            }

            sbtiAnswer81CB.setOnClickListener {
                _answer[8] = 1
                sbtiAnswer82CB.isChecked = false
                sbtiAnswer83CB.isChecked = false
            }
            sbtiAnswer82CB.setOnClickListener {
                _answer[8] = 2
                sbtiAnswer81CB.isChecked = false
                sbtiAnswer83CB.isChecked = false
            }
            sbtiAnswer83CB.setOnClickListener {
                _answer[8] = 3
                sbtiAnswer81CB.isChecked = false
                sbtiAnswer82CB.isChecked = false
            }

            sbtiAnswer91CB.setOnClickListener {
                _answer[9] = 1
                sbtiAnswer92CB.isChecked = false
                sbtiAnswer93CB.isChecked = false
                sbtiAnswer94CB.isChecked = false
                sbtiAnswer95CB.isChecked = false
            }
            sbtiAnswer92CB.setOnClickListener {
                _answer[9] = 2
                sbtiAnswer91CB.isChecked = false
                sbtiAnswer93CB.isChecked = false
                sbtiAnswer94CB.isChecked = false
                sbtiAnswer95CB.isChecked = false
            }
            sbtiAnswer93CB.setOnClickListener {
                _answer[9] = 3
                sbtiAnswer91CB.isChecked = false
                sbtiAnswer92CB.isChecked = false
                sbtiAnswer94CB.isChecked = false
                sbtiAnswer95CB.isChecked = false
            }
            sbtiAnswer94CB.setOnClickListener {
                _answer[9] = 4
                sbtiAnswer91CB.isChecked = false
                sbtiAnswer92CB.isChecked = false
                sbtiAnswer93CB.isChecked = false
                sbtiAnswer95CB.isChecked = false
            }
            sbtiAnswer95CB.setOnClickListener {
                _answer[9] = 5
                sbtiAnswer91CB.isChecked = false
                sbtiAnswer92CB.isChecked = false
                sbtiAnswer93CB.isChecked = false
                sbtiAnswer94CB.isChecked = false
            }

            sbtiAnswer101CB.setOnClickListener {
                _answer[10] = 1
                sbtiAnswer102CB.isChecked = false
                sbtiAnswer103CB.isChecked = false
                sbtiAnswer104CB.isChecked = false
                sbtiAnswer105CB.isChecked = false
            }
            sbtiAnswer102CB.setOnClickListener {
                _answer[10] = 2
                sbtiAnswer101CB.isChecked = false
                sbtiAnswer103CB.isChecked = false
                sbtiAnswer104CB.isChecked = false
                sbtiAnswer105CB.isChecked = false
            }
            sbtiAnswer103CB.setOnClickListener {
                _answer[10] = 3
                sbtiAnswer101CB.isChecked = false
                sbtiAnswer102CB.isChecked = false
                sbtiAnswer104CB.isChecked = false
                sbtiAnswer105CB.isChecked = false
            }
            sbtiAnswer104CB.setOnClickListener {
                _answer[10] = 4
                sbtiAnswer101CB.isChecked = false
                sbtiAnswer102CB.isChecked = false
                sbtiAnswer103CB.isChecked = false
                sbtiAnswer105CB.isChecked = false
            }
            sbtiAnswer105CB.setOnClickListener {
                _answer[10] = 5
                sbtiAnswer101CB.isChecked = false
                sbtiAnswer102CB.isChecked = false
                sbtiAnswer103CB.isChecked = false
                sbtiAnswer104CB.isChecked = false
            }

            sbtiAnswer111CB.setOnClickListener {
                _answer[11] = 1
                sbtiAnswer112CB.isChecked = false
                sbtiAnswer113CB.isChecked = false
                sbtiAnswer114CB.isChecked = false
                sbtiAnswer115CB.isChecked = false
            }
            sbtiAnswer112CB.setOnClickListener {
                _answer[11] = 2
                sbtiAnswer111CB.isChecked = false
                sbtiAnswer113CB.isChecked = false
                sbtiAnswer114CB.isChecked = false
                sbtiAnswer115CB.isChecked = false
            }
            sbtiAnswer113CB.setOnClickListener {
                _answer[11] = 3
                sbtiAnswer111CB.isChecked = false
                sbtiAnswer112CB.isChecked = false
                sbtiAnswer114CB.isChecked = false
                sbtiAnswer115CB.isChecked = false
            }
            sbtiAnswer114CB.setOnClickListener {
                _answer[11] = 4
                sbtiAnswer111CB.isChecked = false
                sbtiAnswer112CB.isChecked = false
                sbtiAnswer113CB.isChecked = false
                sbtiAnswer115CB.isChecked = false
            }
            sbtiAnswer115CB.setOnClickListener {
                _answer[11] = 5
                sbtiAnswer111CB.isChecked = false
                sbtiAnswer112CB.isChecked = false
                sbtiAnswer113CB.isChecked = false
                sbtiAnswer114CB.isChecked = false
            }
        }
    }
}