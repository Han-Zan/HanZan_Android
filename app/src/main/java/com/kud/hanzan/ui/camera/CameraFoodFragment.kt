package com.kud.hanzan.ui.camera

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kud.hanzan.R
import com.kud.hanzan.adapter.camera.CameraResultItemRVAdapter
import com.kud.hanzan.databinding.FragmentCameraFoodBinding
import com.kud.hanzan.ui.dialog.ConfirmDialog
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraFoodFragment : BaseFragment<FragmentCameraFoodBinding>(R.layout.fragment_camera_food){
    private val viewModel by activityViewModels<CameraViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView(){
        with(binding){
            lifecycleOwner = this@CameraFoodFragment
            cameraViewModel = viewModel
            cameraFoodRv.adapter = CameraResultItemRVAdapter().apply {
                setCustomListener(object : CameraResultItemRVAdapter.ItemListener{
                    override fun onDelete(item: String, position: Int) {
                        ConfirmDialog("아이템 삭제", "해당 $item 아이템을 삭제하겠습니까?").apply {
                            setCustomListener(object: ConfirmDialog.DialogConfirmListener{
                                override fun onConfirm() {
                                    dismiss()
                                    removeItem(position)
                                    viewModel.deleteFoodItem(position)
                                    Snackbar.make(binding.root, "삭제가 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                                }
                            })
                        }.show(requireActivity().supportFragmentManager, "confirm")
                    }
                })
            }
        }
    }

    private fun observe(){
        viewModel.foodLiveData.observe(viewLifecycleOwner){
            (binding.cameraFoodRv.adapter as CameraResultItemRVAdapter).setData(it)
        }
    }
}