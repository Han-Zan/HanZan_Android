package com.kud.hanzan.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.kud.hanzan.R
import com.kud.hanzan.databinding.DialogConfirmBinding

class ConfirmDialog(private val title: String, private val content: String) : DialogFragment() {
    private lateinit var binding : DialogConfirmBinding

    interface DialogConfirmListener {
        fun onConfirm()
    }

    private lateinit var dialogListener: DialogConfirmListener

    fun setCustomListener(listener: DialogConfirmListener){
        dialogListener = listener
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
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm, container, false)

        isCancelable = false

        // 모서리 직각 제거
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title = title
        binding.content = content

        binding.confirmDialogCancelBtn.setOnClickListener { dismiss() }
        binding.confirmDialogConfirmBtn.setOnClickListener { dialogListener.onConfirm() }
    }
}