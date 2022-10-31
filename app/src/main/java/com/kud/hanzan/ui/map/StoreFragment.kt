package com.kud.hanzan.ui.map

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentStoreBinding
import com.kud.hanzan.domain.model.map.StoreDetail
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreFragment : BaseFragment<FragmentStoreBinding>(R.layout.fragment_store){
    private val storeArgs by navArgs<StoreFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView(){
        val store = storeArgs.store
        binding.store = StoreDetail(store.id, store.name, store.address, store.phone, 4.2, emptyList())
    }

    private fun initListener(){
        with(binding){
            storeBasicToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            storeKakaoOpenBtn.setOnClickListener {
                try {
                    val url = "kakaomap://place?id=${store?.id}"
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }catch (e: ActivityNotFoundException){
                    val storeUrl = "market://details?id=net.daum.android.map"
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(storeUrl)))
                }
            }
        }
    }
}