package com.kud.hanzan.ui.sbti

import android.content.Intent
import android.view.View
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiBinding
import com.kud.hanzan.utils.base.BaseActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class SbtiActivity : BaseActivity<ActivitySbtiBinding>(R.layout.activity_sbti) {
    // 각 문제들의 답 ([0]은 더미)
    var answer = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    override fun initView() {
        binding.sbtiTipsSUL.isTouchEnabled = false
        initListener()
    }

    private fun initListener(){
        with(binding) {
            sbtiTipNextBtn.setOnClickListener {
                sbtiTestLayout.visibility = View.VISIBLE
                sbtiTipsSUL.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                sbtiTipNextBtn.visibility = View.INVISIBLE
                progress = 0
                sbtiProgress.visibility = View.VISIBLE
                initSbtiListener()
            }
        }
        binding.sbtiNextBtn.setOnClickListener {
            startActivity(Intent(this, SbtiResultActivity::class.java))
        }
    }

    private fun initSbtiListener(){
        with(binding){
            sbtiAnswer11CB.setOnClickListener {
                if (!sbtiAnswer11CB.isChecked) answer[1] = 0 else answer[1] = 1
                sbtiAnswer12CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion1.top)
            }
            sbtiAnswer12CB.setOnClickListener {
                if (!sbtiAnswer12CB.isChecked) answer[1] = 0 else answer[1] = 2
                sbtiAnswer11CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion1.top)
            }

            sbtiAnswer21CB.setOnClickListener {
                if (!sbtiAnswer21CB.isChecked) answer[2] = 0 else answer[2] = 1
                sbtiAnswer22CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion2.top)
            }
            sbtiAnswer22CB.setOnClickListener {
                if (!sbtiAnswer22CB.isChecked) answer[2] = 0 else answer[2] = 2
                sbtiAnswer21CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion2.top)
            }

            sbtiAnswer31CB.setOnClickListener {
                if (!sbtiAnswer31CB.isChecked) answer[3] = 0 else answer[3] = 1
                sbtiAnswer32CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion3.top)
            }
            sbtiAnswer32CB.setOnClickListener {
                if (!sbtiAnswer32CB.isChecked) answer[3] = 0 else answer[3] = 2
                sbtiAnswer31CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion3.top)
            }

            sbtiAnswer41CB.setOnClickListener {
                if (!sbtiAnswer41CB.isChecked) answer[4] = 0 else answer[4] = 1
                sbtiAnswer42CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion4.top)
            }
            sbtiAnswer42CB.setOnClickListener {
                if (!sbtiAnswer42CB.isChecked) answer[4] = 0 else answer[4] = 2
                sbtiAnswer41CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion4.top)
            }

            sbtiAnswer51CB.setOnClickListener {
                if (!sbtiAnswer51CB.isChecked) answer[5] = 0 else answer[5] = 1
                sbtiAnswer52CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion5.top)
            }
            sbtiAnswer52CB.setOnClickListener {
                if (!sbtiAnswer52CB.isChecked) answer[5] = 0 else answer[5] = 2
                sbtiAnswer51CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion5.top)
            }

            sbtiAnswer61CB.setOnClickListener {
                if (!sbtiAnswer61CB.isChecked) answer[6] = 0 else answer[6] = 1
                sbtiAnswer62CB.isChecked = false
                sbtiAnswer63CB.isChecked = false
                sbtiAnswer64CB.isChecked = false
                sbtiAnswer65CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion6.top)
            }
            sbtiAnswer62CB.setOnClickListener {
                if (!sbtiAnswer62CB.isChecked) answer[6] = 0 else answer[6] = 2
                sbtiAnswer61CB.isChecked = false
                sbtiAnswer63CB.isChecked = false
                sbtiAnswer64CB.isChecked = false
                sbtiAnswer65CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion6.top)
            }
            sbtiAnswer63CB.setOnClickListener {
                if (!sbtiAnswer63CB.isChecked) answer[6] = 0 else answer[6] = 3
                sbtiAnswer61CB.isChecked = false
                sbtiAnswer62CB.isChecked = false
                sbtiAnswer64CB.isChecked = false
                sbtiAnswer65CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion6.top)
            }
            sbtiAnswer64CB.setOnClickListener {
                if (!sbtiAnswer64CB.isChecked) answer[6] = 0 else answer[6] = 4
                sbtiAnswer61CB.isChecked = false
                sbtiAnswer62CB.isChecked = false
                sbtiAnswer63CB.isChecked = false
                sbtiAnswer65CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion6.top)
            }
            sbtiAnswer65CB.setOnClickListener {
                if (!sbtiAnswer65CB.isChecked) answer[6] = 0 else answer[6] = 5
                sbtiAnswer61CB.isChecked = false
                sbtiAnswer62CB.isChecked = false
                sbtiAnswer63CB.isChecked = false
                sbtiAnswer64CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion6.top)
            }

            sbtiAnswer71CB.setOnClickListener {
                if (!sbtiAnswer71CB.isChecked) answer[7] = 0 else answer[7] = 1
                sbtiAnswer72CB.isChecked = false
                sbtiAnswer73CB.isChecked = false
                sbtiAnswer74CB.isChecked = false
                sbtiAnswer75CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion7.top)
            }
            sbtiAnswer72CB.setOnClickListener {
                if (!sbtiAnswer72CB.isChecked) answer[7] = 0 else answer[7] = 2
                sbtiAnswer71CB.isChecked = false
                sbtiAnswer73CB.isChecked = false
                sbtiAnswer74CB.isChecked = false
                sbtiAnswer75CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion7.top)
            }
            sbtiAnswer73CB.setOnClickListener {
                if (!sbtiAnswer73CB.isChecked) answer[7] = 0 else answer[7] = 3
                sbtiAnswer71CB.isChecked = false
                sbtiAnswer72CB.isChecked = false
                sbtiAnswer74CB.isChecked = false
                sbtiAnswer75CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion7.top)
            }
            sbtiAnswer74CB.setOnClickListener {
                if (!sbtiAnswer74CB.isChecked) answer[7] = 0 else answer[7] = 4
                sbtiAnswer71CB.isChecked = false
                sbtiAnswer72CB.isChecked = false
                sbtiAnswer73CB.isChecked = false
                sbtiAnswer75CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion7.top)
            }
            sbtiAnswer75CB.setOnClickListener {
                if (!sbtiAnswer75CB.isChecked) answer[7] = 0 else answer[7] = 5
                sbtiAnswer71CB.isChecked = false
                sbtiAnswer72CB.isChecked = false
                sbtiAnswer73CB.isChecked = false
                sbtiAnswer74CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion7.top)
            }

            sbtiAnswer81CB.setOnClickListener {
                if (!sbtiAnswer81CB.isChecked) answer[8] = 0 else answer[8] = 1
                sbtiAnswer82CB.isChecked = false
                sbtiAnswer83CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion8.top)
            }
            sbtiAnswer82CB.setOnClickListener {
                if (!sbtiAnswer82CB.isChecked) answer[8] = 0 else answer[8] = 2
                sbtiAnswer81CB.isChecked = false
                sbtiAnswer83CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion8.top)
            }
            sbtiAnswer83CB.setOnClickListener {
                if (!sbtiAnswer83CB.isChecked) answer[8] = 0 else answer[8] = 3
                sbtiAnswer81CB.isChecked = false
                sbtiAnswer82CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion8.top)
            }

            sbtiAnswer91CB.setOnClickListener {
                if (!sbtiAnswer91CB.isChecked) answer[9] = 0 else answer[9] = 1
                sbtiAnswer92CB.isChecked = false
                sbtiAnswer93CB.isChecked = false
                sbtiAnswer94CB.isChecked = false
                sbtiAnswer95CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion9.top)
            }
            sbtiAnswer92CB.setOnClickListener {
                if (!sbtiAnswer92CB.isChecked) answer[9] = 0 else answer[9] = 2
                sbtiAnswer91CB.isChecked = false
                sbtiAnswer93CB.isChecked = false
                sbtiAnswer94CB.isChecked = false
                sbtiAnswer95CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion9.top)
            }
            sbtiAnswer93CB.setOnClickListener {
                if (!sbtiAnswer93CB.isChecked) answer[9] = 0 else answer[9] = 3
                sbtiAnswer91CB.isChecked = false
                sbtiAnswer92CB.isChecked = false
                sbtiAnswer94CB.isChecked = false
                sbtiAnswer95CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion9.top)
            }
            sbtiAnswer94CB.setOnClickListener {
                if (!sbtiAnswer94CB.isChecked) answer[9] = 0 else answer[9] = 4
                sbtiAnswer91CB.isChecked = false
                sbtiAnswer92CB.isChecked = false
                sbtiAnswer93CB.isChecked = false
                sbtiAnswer95CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion9.top)
            }
            sbtiAnswer95CB.setOnClickListener {
                if (!sbtiAnswer95CB.isChecked) answer[9] = 0 else answer[9] = 5
                sbtiAnswer91CB.isChecked = false
                sbtiAnswer92CB.isChecked = false
                sbtiAnswer93CB.isChecked = false
                sbtiAnswer94CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion9.top)
            }

            sbtiAnswer101CB.setOnClickListener {
                if (!sbtiAnswer101CB.isChecked) answer[10] = 0 else answer[10] = 1
                sbtiAnswer102CB.isChecked = false
                sbtiAnswer103CB.isChecked = false
                sbtiAnswer104CB.isChecked = false
                sbtiAnswer105CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion10.top)
            }
            sbtiAnswer102CB.setOnClickListener {
                if (!sbtiAnswer102CB.isChecked) answer[10] = 0 else answer[10] = 2
                sbtiAnswer101CB.isChecked = false
                sbtiAnswer103CB.isChecked = false
                sbtiAnswer104CB.isChecked = false
                sbtiAnswer105CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion10.top)
            }
            sbtiAnswer103CB.setOnClickListener {
                if (!sbtiAnswer103CB.isChecked) answer[10] = 0 else answer[10] = 3
                sbtiAnswer101CB.isChecked = false
                sbtiAnswer102CB.isChecked = false
                sbtiAnswer104CB.isChecked = false
                sbtiAnswer105CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion10.top)
            }
            sbtiAnswer104CB.setOnClickListener {
                if (!sbtiAnswer104CB.isChecked) answer[10] = 0 else answer[10] = 4
                sbtiAnswer101CB.isChecked = false
                sbtiAnswer102CB.isChecked = false
                sbtiAnswer103CB.isChecked = false
                sbtiAnswer105CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion10.top)
            }
            sbtiAnswer105CB.setOnClickListener {
                if (!sbtiAnswer105CB.isChecked) answer[10] = 0 else answer[10] = 5
                sbtiAnswer101CB.isChecked = false
                sbtiAnswer102CB.isChecked = false
                sbtiAnswer103CB.isChecked = false
                sbtiAnswer104CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion10.top)
            }

            sbtiAnswer111CB.setOnClickListener {
                if (!sbtiAnswer111CB.isChecked) answer[11] = 0 else answer[10] = 1
                sbtiAnswer112CB.isChecked = false
                sbtiAnswer113CB.isChecked = false
                sbtiAnswer114CB.isChecked = false
                sbtiAnswer115CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion11.top)
            }
            sbtiAnswer112CB.setOnClickListener {
                if (!sbtiAnswer112CB.isChecked) answer[11] = 0 else answer[11] = 2
                sbtiAnswer111CB.isChecked = false
                sbtiAnswer113CB.isChecked = false
                sbtiAnswer114CB.isChecked = false
                sbtiAnswer115CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion11.top)
            }
            sbtiAnswer113CB.setOnClickListener {
                if (!sbtiAnswer113CB.isChecked) answer[11] = 0 else answer[11] = 3
                sbtiAnswer111CB.isChecked = false
                sbtiAnswer112CB.isChecked = false
                sbtiAnswer114CB.isChecked = false
                sbtiAnswer115CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion11.top)
            }
            sbtiAnswer114CB.setOnClickListener {
                if (!sbtiAnswer114CB.isChecked) answer[11] = 0 else answer[11] = 4
                sbtiAnswer111CB.isChecked = false
                sbtiAnswer112CB.isChecked = false
                sbtiAnswer113CB.isChecked = false
                sbtiAnswer115CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion11.top)
            }
            sbtiAnswer115CB.setOnClickListener {
                if (!sbtiAnswer115CB.isChecked) answer[11] = 0 else answer[11] = 5
                sbtiAnswer111CB.isChecked = false
                sbtiAnswer112CB.isChecked = false
                sbtiAnswer113CB.isChecked = false
                sbtiAnswer114CB.isChecked = false
                updateProgress()
                sbtiTestLayout.smoothScrollTo(0, sbtiQuestion11.top)
            }
        }
    }

    private fun updateProgress() {
        var count = 0
        for (index in 1 until 12) {
            if (answer[index] != 0) count++
        }
        val value = (count*100).div(11)
        with(binding) {
            progress = value
            isTestOver = (progress == 100)
        }
    }
}