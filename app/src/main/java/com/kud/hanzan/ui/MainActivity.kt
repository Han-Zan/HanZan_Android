package com.kud.hanzan.ui

import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityMainBinding
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main){
    override fun initView() {
        initBottomNav()
    }

    private fun initBottomNav(){
        val navController = supportFragmentManager.findFragmentById(R.id.main_fragment_container)?.findNavController()
        navController?.let { binding.mainBottomNav.setupWithNavController(it) }
//        binding.mainBottomNav.setOnItemSelectedListener {
//            item ->
//                if (item.itemId == R.id.searchActivity)
//                    startActivity(Intent(this, SearchActivity::class.java))
//                else {
//                    navController?.let {
//                        NavigationUI.onNavDestinationSelected(item, it) }
//                }
//            return@setOnItemSelectedListener true
//        }
//        binding.mainBottomNav.selectedItemId = R.id.homeFragment
    }
}