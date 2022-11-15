package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentCombinationBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombinationFragment : BaseFragment<FragmentCombinationBinding>(R.layout.fragment_combination){
    private val combNavArgs by navArgs<CombinationFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }
    private fun initView(){
        combNavArgs.drink?.let {
            binding.drink = it
        }
    }


    private fun initListener(){
        with(binding){
            combinationDrinkBtn.setOnClickListener {
                val action = CombinationFragmentDirections.actionCombinationFragmentToDrinkListFragment()
                findNavController().navigate(action)
            }
            combinationFoodBtn.setOnClickListener {
                val action = CombinationFragmentDirections.actionCombinationFragmentToFoodCategoryFragment()
                findNavController().navigate(action)
            }
        }
    }
}