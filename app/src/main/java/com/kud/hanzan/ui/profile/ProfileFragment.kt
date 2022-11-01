package com.kud.hanzan.ui.profile

import android.os.Bundle
import android.view.View
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentProfileBinding
import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.userInfo = UserInfo("이동건", "사용자", 20,  "고독한 미식가형", " ",0, true)
    }
}