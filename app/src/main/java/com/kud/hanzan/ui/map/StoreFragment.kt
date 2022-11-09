package com.kud.hanzan.ui.map

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentStoreBinding
import com.kud.hanzan.domain.model.map.StoreDetail
import com.kud.hanzan.ui.dialog.ImageSelectDialog
import com.kud.hanzan.utils.URIPathHelper
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


@AndroidEntryPoint
class StoreFragment : BaseFragment<FragmentStoreBinding>(R.layout.fragment_store){
    companion object{
        private const val TAG = "Store"
        private const val REQUIRED_EXTERNAL_STORAGE_PERMISSIONS = Manifest.permission.READ_EXTERNAL_STORAGE
    }

    private val viewModel by viewModels<StoreViewModel>()

    private val storeArgs by navArgs<StoreFragmentArgs>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView(){
        val store = storeArgs.store
        binding.store = StoreDetail(store.id.toLong(), store.name, store.address, store.phone, 4.2, emptyList(), emptyList())

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if(it.resultCode == Activity.RESULT_OK){
                showDialog(it.data?.data)
            }
        }
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
            storeImageUploadBtn.setOnClickListener {
                requestPermissionLauncher.launch(REQUIRED_EXTERNAL_STORAGE_PERMISSIONS)
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

    private fun showDialog(imgUri: Uri?){
        imgUri?.let {
            val dialog = ImageSelectDialog(it).apply {
                setCustomListener(object : ImageSelectDialog.ImageSelectListener{
                    override fun onConfirm() {
                        dismiss()
                        imgUri?.let { u ->
                            val file = File(URIPathHelper().getPath(requireContext(), u))
                            Log.e("storeImage Upload", "${file.name}, $file")
                            viewModel.uploadImage(storeArgs.store.name + file.name, file)
                        }
                    }
                })
            }
            dialog.show(requireActivity().supportFragmentManager, "이미지 선택")
        }
    }
}