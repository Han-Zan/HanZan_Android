package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.kud.hanzan.R
import com.kud.hanzan.adapter.DrinkRVAdapter
import com.kud.hanzan.databinding.FragmentDrinkListBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DrinkListFragment : BaseFragment<FragmentDrinkListBinding>(R.layout.fragment_drink_list) {
    private val viewModel by viewModels<DrinkListViewModel>()
    companion object{
        private val tabInfo = listOf("전체", "소주", "맥주", "양주", "와인", "기타")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observe()
    }

    private fun initView(){
        binding.viewModel = viewModel
        binding.drinkListTab.apply {
            tabInfo.forEach { t -> addTab(this.newTab().setText(t))  }
            addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        viewModel.setDrinkListType(it)
                        Log.e("id position", it.toString())
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    //TODO("Not yet implemented")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    //TODO("Not yet implemented")
                }

            })
        }
        binding.drinkListRv.apply {
            adapter = DrinkRVAdapter().apply {
                setListener(object : DrinkRVAdapter.Listener{
                    override fun onDelete(drinkId: Long) {
                        viewModel.deleteDrink(drinkId)
                    }

                    override fun onPost(drinkId: Long) {
                        viewModel.postDrink(drinkId)
                    }

                })
            }
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun initListener(){
        with(binding){
            drinkListToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observe(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.drinkList.collectLatest {
                        (binding.drinkListRv.adapter as DrinkRVAdapter).setData(it)
                    }
                }
            }
        }
    }
}