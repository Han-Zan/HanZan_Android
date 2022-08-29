package com.kud.hanzan.ui

import com.google.android.material.snackbar.Snackbar
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityMainBinding
import com.kud.hanzan.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }
    }
}