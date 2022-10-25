package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentDrinkListBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkListFragment : BaseFragment<FragmentDrinkListBinding>(R.layout.fragment_drink_list) {
    companion object{
        private val tabInfo = listOf("전체", "소주", "맥주", "양주", "와인", "기타")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView(){
        binding.drinkListTab.apply {
            tabInfo.forEach { t -> addTab(this.newTab().setText(t))  }
        }
    }

    private fun initListener(){
        with(binding){
            drinkListToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}