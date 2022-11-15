package com.kud.hanzan.ui.profile

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.kakao.sdk.user.UserApiClient
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.R
import com.kud.hanzan.databinding.DialogOneEditTextBinding
import com.kud.hanzan.databinding.FragmentProfileBinding
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.ui.dialog.ImageSelectDialog
import com.kud.hanzan.ui.dialog.OneEditTextDialog
import com.kud.hanzan.ui.map.StoreFragment
import com.kud.hanzan.utils.URIPathHelper
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel by viewModels<ProfileViewModel>()

    companion object{
        private const val REQUIRED_EXTERNAL_STORAGE_PERMISSIONS = Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // 사진 받기
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    // 저장소 권한
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted){
            Log.e("storeFragment", "외부 저장소 읽기")
            openStorage()
        } else {
            Toast.makeText(requireContext(), "저장소 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

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

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if(it.resultCode == Activity.RESULT_OK){
                showDialog(it.data?.data)
            }
        }
    }

    private var pressedTime: Long = 0
    private fun initListener() {
        with(binding) {
            profileUserChangeNicknameBtn.setOnClickListener {
                OneEditTextDialog("닉네임 변경", "변경할 닉네임을 입력해주세요.").apply {
                    setCustomListener(object: OneEditTextDialog.DialogOneEditTextListener{
                        override fun onConfirm() {
                            viewModel.changeUserNickName(userId, getText())
                            dismiss()
                        }
                    })
                }.show(requireActivity().supportFragmentManager, "changeNickname")
            }
            profileUserChangeProfileBtn.setOnClickListener {
                requestPermissionLauncher.launch(ProfileFragment.REQUIRED_EXTERNAL_STORAGE_PERMISSIONS)
            }
            profileSbtiBtn.setOnClickListener {

            }
            profileUserDeleteBtn.setOnClickListener {
                if (System.currentTimeMillis() <= pressedTime + 2000) {
                    // 뒤로가기 두 번 누르면 종료
                    viewModel.deleteUser(userId)
                } else{
                    pressedTime = System.currentTimeMillis()
                    Toast.makeText(context, "버튼을 연속으로 두 번 누르면 회원 탈퇴가 정상적으로 이루어집니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showDialog(imgUri: Uri?){
        imgUri?.let {
            val dialog = ImageSelectDialog(it).apply {
                setCustomListener(object : ImageSelectDialog.ImageSelectListener{
                    override fun onConfirm() {
                        dismiss()
                        viewModel.changeUserProfile(userId, imgUri.toString())
                    }
                })
            }
            dialog.show(requireActivity().supportFragmentManager, "이미지 선택")
        }
    }

    private fun observe() {
        with(viewModel) {
            userLiveData.observe(viewLifecycleOwner) {
                binding.user = User(it.id, it.kakaoId, it.male, it.nickname, it.profileimage, it.sbti, it.userage, it.username)
                Log.e(TAG, binding.user.toString())
            }
            resChangeNickNameLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(context, "정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                viewModel.getUser(userId)
            }
            resChangeProfileLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(context, "정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                viewModel.getUser(userId)
            }
            resDeleteLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(context, "정상적으로 탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                kakaoDelete()
                HanZanApplication.spfManager.spfClear()
                activity?.finishAffinity()
            }
        }
    }

    private fun openStorage(){
        activityResultLauncher.launch(
            Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
        )
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