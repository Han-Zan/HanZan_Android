package com.kud.hanzan.ui.dialog

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.kud.hanzan.R
import com.kud.hanzan.databinding.DialogImageSelectBinding

class ImageSelectDialog(private val imgUri: Uri) : DialogFragment() {
    private lateinit var binding: DialogImageSelectBinding

    interface ImageSelectListener {
        fun onConfirm()
    }

    private lateinit var imageSelectListener: ImageSelectListener

    fun setCustomListener(listener: ImageSelectListener){
        imageSelectListener = listener
    }

    override fun onResume() {
        super.onResume()

        // 디바이스 크기별 세팅
        val params = dialog?.window?.attributes
        val windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val size = windowManager.currentWindowMetrics
            val deviceWidth = size.bounds.width()

            params?.width = (deviceWidth * 0.9).toInt()
            dialog?.window?.attributes = params as WindowManager.LayoutParams
        } else {
            // Todo : Android 11 미만 버전용
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_image_select, container, false)
        isCancelable = false
        // 모서리 직각 제거
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            setImgFromUri()
            dialogImageCancelBtn.setOnClickListener { dismiss() }
            dialogImageUploadBtn.setOnClickListener { imageSelectListener.onConfirm() }
        }
    }

    private fun setImgFromUri(){
        val bitmap: Bitmap
        if (Build.VERSION.SDK_INT < 28) {
            bitmap = MediaStore.Images.Media.getBitmap(
                requireActivity().contentResolver,
                imgUri
            )
            binding.dialogImageUploadIv.setImageBitmap(bitmap)
        } else {
            val source =
                ImageDecoder.createSource(requireActivity().contentResolver, imgUri)
            bitmap = ImageDecoder.decodeBitmap(source)
            binding.dialogImageUploadIv.setImageBitmap(bitmap)
        }
    }
}