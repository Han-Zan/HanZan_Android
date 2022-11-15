package com.kud.hanzan.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kud.hanzan.R
import com.kud.hanzan.databinding.BottomSheetBinding

class BottomSheet(private val title: String, private val content: String)
    : BottomSheetDialogFragment(){
    private lateinit var binding : BottomSheetBinding

    interface BottomSheetListener{
        fun onConfirm()
    }

    private lateinit var customListener: BottomSheetListener

    fun setCustomListener(mClickListener: BottomSheetListener){
        customListener = mClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false)
        binding.title = title
        binding.content = content
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            bottomSheetCancelBtn.setOnClickListener { dismiss() }
            bottomSheetConfirmBtn.setOnClickListener { customListener.onConfirm() }
        }
    }
}