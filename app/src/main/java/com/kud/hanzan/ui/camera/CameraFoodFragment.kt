package com.kud.hanzan.ui.camera

import android.app.Activity
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
import com.kud.hanzan.databinding.FragmentCameraFoodBinding
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.ui.dialog.ConfirmDialog
import com.kud.hanzan.ui.dialog.OneEditTextDialog
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraFoodFragment : BaseFragment<FragmentCameraFoodBinding>(R.layout.fragment_camera_food){
    private val viewModel by activityViewModels<CameraResultViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observe()
    }

    private var foodList = listOf<Food>()
    private fun initView(){
        viewModel.getAllFoodList()
        with(binding){
            lifecycleOwner = this@CameraFoodFragment
            cameraViewModel = viewModel
            cameraFoodRv.apply {
                adapter = CameraResultItemRVAdapter().apply {
                    setCustomListener(object : CameraResultItemRVAdapter.ItemListener {
                        override fun onDelete(item: String, position: Int) {
                            ConfirmDialog("아이템 삭제", "해당 $item 아이템을 삭제하겠습니까?").apply {
                                setCustomListener(object : ConfirmDialog.DialogConfirmListener {
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
                layoutManager = FlexboxLayoutManager(requireContext())
            }
        }
    }

    private fun initListener(){
        with(binding){
            cameraFoodAgainBtn.setOnClickListener {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.apply {
                    putExtra("drinkList", viewModel.drinkLiveData.value?.toTypedArray())
                    putExtra("foodList", viewModel.foodLiveData.value?.toTypedArray())
                    putExtra("drinkMode", false)
                }
                requireActivity().apply {
                    setResult(Activity.RESULT_OK, intent)
                    onBackPressed()
                }
            }
            cameraFoodAddBtn.setOnClickListener {
                OneEditTextDialog("안주 직접 추가하기", "안주의 이름을 입력해주세요.").apply {
                    setCustomListener(object: OneEditTextDialog.DialogOneEditTextListener{
                        override fun onConfirm() {
                            if (foodList.count{it.name == getText()} != 0) {
                                viewModel.addFoodData(getText())
                                (binding.cameraFoodRv.adapter as CameraResultItemRVAdapter).addData(getText())
                            } else {
                                Toast.makeText(requireContext(), "등록되어 있지 않은 안주입니다.", Toast.LENGTH_SHORT).show()
                            }
                            dismiss()
                        }
                    })
                }.show(requireActivity().supportFragmentManager, "addFood")
            }
        }
    }

    private fun observe(){
        viewModel.foodLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                (binding.cameraFoodRv.adapter as CameraResultItemRVAdapter).setData(it)
                Log.e("cameraFragment observe", it.toString())
            } else if (!viewModel.isDrinkMode){
                // 인식된 안주 데이터가 없고 안주 인식 모드로 촬영한 경우
                Toast.makeText(requireContext(), "인식된 안주 데이터가 없습니다.\n다시 찍거나 직접 추가해주세요!", Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.foodListLiveData.observe(viewLifecycleOwner) {
            foodList = it
        }
    }
}