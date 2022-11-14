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
import com.kud.hanzan.databinding.DialogOneEditTextBinding

class OneEditTextDialog(private val title: String, private val hint: String) : DialogFragment() {
    private lateinit var binding : DialogOneEditTextBinding

    fun getText() : String{
        return binding.oneEditTextET.text.toString()
    }

    interface DialogOneEditTextListener {
        fun onConfirm()
    }

    private lateinit var dialogListener: DialogOneEditTextListener

    fun setCustomListener(listener: DialogOneEditTextListener){
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

            params?.width = (deviceWidth * 0.95).toInt()
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
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_one_edit_text, container, false)

        binding.title = title
        binding.hint = hint

        isCancelable = false

        // 모서리 직각 제거
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.oneEditTextCancelBtn.setOnClickListener { dismiss() }
        binding.oneEditTextConfirmBtn.setOnClickListener { dialogListener.onConfirm() }
    }
}