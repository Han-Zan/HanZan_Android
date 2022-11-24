package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentDrinkBinding
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.ui.dialog.BottomSheet
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkFragment : BaseFragment<FragmentDrinkBinding>(R.layout.fragment_drink){
    private val viewModel by viewModels<DrinkViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observe()
    }

    private fun initView(){
        binding.lifecycleOwner = this
    }

    private fun initListener(){
        with(binding){
            drinkToolbar.setNavigationOnClickListener {
                findNavController().apply {
                    popBackStack()
                }
            }
            drinkSelectBtn.setOnClickListener {
                drink?.let {
                    val bottomSheet = BottomSheet(
                        it.name + " 선택",
                        resources.getString(R.string.drink_alert_bottom_sheet)
                    ).apply {
                        setCustomListener(object : BottomSheet.BottomSheetListener {
                            override fun onConfirm() {
                                dismiss()

                                val action = DrinkFragmentDirections.actionDrinkFragmentToCombinationFragment(it, null)
                                findNavController().navigate(action)
                            }
                            }
                        )
                    }
                    bottomSheet.show(requireActivity().supportFragmentManager, "bottomSheet")
                }
            }
            likeDrinkCb.setOnClickListener {
                drink?.id?.let {
                    if (!likeDrinkCb.isChecked)
                        viewModel.deleteDrinkLike(it)
                    else viewModel.postDrinkLike(it)
                }
            }
        }
    }

    private fun observe(){
        viewModel.drinkData.observe(viewLifecycleOwner){
            binding.drink = it
        }
    }
}