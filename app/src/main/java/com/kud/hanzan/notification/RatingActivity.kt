package com.kud.hanzan.notification

import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityRatingBinding
import com.kud.hanzan.ui.dialog.ConfirmDialog
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingActivity : BaseActivity<ActivityRatingBinding>(R.layout.activity_rating){
    override fun initView() {
        initListener()
    }

    private fun initListener(){
        with(binding){
            lifecycleOwner = this@RatingActivity
            ratingBar.setOnRatingBarChangeListener { _, fl, b ->
                buttonVisible = b && fl > 0
            }
        }
    }

    override fun onBackPressed() {
        ConfirmDialog(resources.getString(R.string.rating_dismiss_title), resources.getString(R.string.rating_dismiss_content)).apply {
            setCustomListener(object : ConfirmDialog.DialogConfirmListener{
                override fun onConfirm() {
                    dismiss()
                    finish()
                }
            })
        }.show(supportFragmentManager, "ratingDialog")
    }
}