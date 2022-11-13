package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentDrinkBinding
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkFragment : BaseFragment<FragmentDrinkBinding>(R.layout.fragment_drink){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.drink = Drink(1, "참이슬", 1, 4.2f, null, "소주는 역시", true)
    }
}