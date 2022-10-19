package com.kud.hanzan.ui.like

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.like.LikeAlcoholRVAdapter
import com.kud.hanzan.databinding.FragmentLikeAlcoholBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LikeAlcoholFragment : BaseFragment<FragmentLikeAlcoholBinding>(R.layout.fragment_like_alcohol) {
    private val viewModel by viewModels<LikeViewModel> (ownerProducer = {requireParentFragment()})
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
        observe()
    }

    private fun initListener(){
        binding.likeViewModel = viewModel
        with(binding){
            lifecycleOwner = this@LikeAlcoholFragment

            likeKeywordRg.setOnCheckedChangeListener { _, id ->
                when(id){
                    R.id.like_keyword_type_01_rb -> viewModel.setTypeAlcohol(1)
                    R.id.like_keyword_type_02_rb -> viewModel.setTypeAlcohol(2)
                    R.id.like_keyword_type_03_rb -> viewModel.setTypeAlcohol(3)
                    R.id.like_keyword_type_04_rb -> viewModel.setTypeAlcohol(4)
                    R.id.like_keyword_type_05_rb -> viewModel.setTypeAlcohol(5)
                    else -> viewModel.setTypeAlcohol(0)
                }
            }
            // popup menu
            val listPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listPopupWindow.apply {
                anchorView = likePreferredSortBtn
                val items = listOf("최근 찜한 순", "상품 이름 순")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_like_sort, items)
                setAdapter(adapter)
                setOnItemClickListener { adapterView, view, position, id ->
                    likePreferredSortBtn.text = items[position]
                    // 팝업 닫기
                    viewModel.setAlcoholSort(position == 0)
                    dismiss()
                }
            }
            likePreferredSortBtn.setOnClickListener {
                listPopupWindow.show()
            }
        }
    }

    private fun initView(){
        binding.likePreferredRv.apply {
            adapter = LikeAlcoholRVAdapter().apply {
                setListener(object : LikeAlcoholRVAdapter.Listener{
                    override fun onDelete(drinkId: Long) {
                        viewModel.deleteDrink(1, drinkId)
                    }

                    override fun onPost(drinkId: Long) {
                        viewModel.postDrink(1, drinkId)
                    }

                })
            }
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun observe(){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.alcoholData.collectLatest {
                    (binding.likePreferredRv.adapter as LikeAlcoholRVAdapter).setData(it)
                }
            }
        }
    }
}