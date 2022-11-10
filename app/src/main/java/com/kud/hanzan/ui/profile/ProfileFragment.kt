package com.kud.hanzan.ui.profile

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.kakao.sdk.user.UserApiClient
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.R
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.databinding.FragmentProfileBinding
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.ui.sbti.SbtiResultViewModel
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel by viewModels<ProfileViewModel>()

    private var userId: Long = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observe()
    }

    private fun initView() {
        userId = HanZanApplication.spfManager.getUserIdx()
        viewModel.getUser(userId)
    }

    private var pressedTime: Long = 0
    private fun initListener() {
        binding.profileUserDeleteBtn.setOnClickListener {
            if (System.currentTimeMillis() <= pressedTime + 2000) {
                // 뒤로가기 두 번 누르면 종료
                viewModel.deleteUser(userId)
            } else{
                pressedTime = System.currentTimeMillis()
                Toast.makeText(context, "버튼을 연속으로 두 번 누르면 회원 탈퇴가 정상적으로 이루어집니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observe() {
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.user = User(it.id, it.kakaoId, it.male, it.nickname, it.profileimage, it.sbti, it.userage, it.username)
            Log.e(TAG, binding.user.toString())
        }
        viewModel.resChangeNickNameLiveData.observe(viewLifecycleOwner) {
            if (it == "Success") Toast.makeText(context, "정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
            else Toast.makeText(context, "변경에 문제가 발생했습니다. 잠시후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        }
        viewModel.resChangeProfileLiveData.observe(viewLifecycleOwner) {
            if (it == "Success") Toast.makeText(context, "정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
            else Toast.makeText(context, "변경에 문제가 발생했습니다. 잠시후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        }
        viewModel.resDeleteLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, "정상적으로 탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
            kakaoDelete()
            HanZanApplication.spfManager.spfClear()
            activity?.finishAffinity()
        }
    }

    private fun kakaoDelete() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "연결 끊기 실패", error)
            }
            else {
                Log.i(ContentValues.TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }
}