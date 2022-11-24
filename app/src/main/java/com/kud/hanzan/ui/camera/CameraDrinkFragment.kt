package com.kud.hanzan.ui.camera

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kud.hanzan.R
import com.kud.hanzan.adapter.camera.CameraResultItemRVAdapter
import com.kud.hanzan.databinding.FragmentCameraDrinkBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.ui.dialog.ConfirmDialog
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraDrinkFragment : BaseFragment<FragmentCameraDrinkBinding>(R.layout.fragment_camera_drink) {
    private val viewModel by activityViewModels<CameraResultViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observe()
    }

    private fun initView(){
        with(binding){
            lifecycleOwner = this@CameraDrinkFragment
            cameraViewModel = viewModel
            cameraDrinkRv.apply {
                adapter = CameraResultItemRVAdapter().apply {
                    setCustomListener(object : CameraResultItemRVAdapter.ItemListener {
                        override fun onDelete(item: String, position: Int) {
                            ConfirmDialog("아이템 삭제", "해당 $item 아이템을 삭제하겠습니까?").apply {
                                setCustomListener(object: ConfirmDialog.DialogConfirmListener{
                                    override fun onConfirm() {
                                        dismiss()
                                        removeItem(position)
                                        viewModel.deleteDrinkItem(position)
                                        Snackbar.make(binding.root, "삭제가 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                                    }
                                })
                            }.show(requireActivity().supportFragmentManager, "confirm")
                        }
                    })
                }
                layoutManager = FlexboxLayoutManager(requireContext())
            }
        }
    }

    private fun initListener(){
        with(binding){
            cameraDrinkAgainBtn.setOnClickListener {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.apply {
                    putExtra("drinkList", viewModel.drinkLiveData.value?.toTypedArray())
                    putExtra("foodList", viewModel.foodLiveData.value?.toTypedArray())
                    putExtra("drinkMode", true)
                }
                requireActivity().apply {
                    setResult(RESULT_OK, intent)
                    onBackPressed()
                }
            }
        }
    }

    private fun observe(){
        viewModel.drinkLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                (binding.cameraDrinkRv.adapter as CameraResultItemRVAdapter).setData(it)
                Log.e("camera ocr data", it.toString())
            } else if (viewModel.isDrinkMode){
                // 인식된 술 데이터가 없고 술 인식 모드로 촬영한 경우
                Toast.makeText(requireContext(), "인식된 술 데이터가 없습니다.\n다시 찍거나 직접 추가해주세요!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}