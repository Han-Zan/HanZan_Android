package com.kud.hanzan.ui.camera

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kud.hanzan.R
import com.kud.hanzan.adapter.camera.CameraResultItemRVAdapter
import com.kud.hanzan.databinding.ActivityCameraResultBinding
import com.kud.hanzan.ui.dialog.ConfirmDialog
import com.kud.hanzan.utils.base.BaseActivity
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraResultActivity : BaseActivity<ActivityCameraResultBinding>(R.layout.activity_camera_result){
    private val viewModel by viewModels<CameraViewModel>()

    override fun initView(){
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        with(binding){
            cameraResultItemFoodRv.apply {
                adapter = CameraResultItemRVAdapter().apply {
                    setCustomListener(object : CameraResultItemRVAdapter.ItemListener{
                        override fun onDelete(item: String, position: Int){
                            ConfirmDialog("아이템 삭제", "해당 $item 아이템을 삭제하겠습니까?").apply {
                                setCustomListener(object: ConfirmDialog.DialogConfirmListener{
                                    override fun onConfirm() {
                                        dismiss()
                                        removeItem(position)
                                        Snackbar.make(binding.root, "삭제가 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                                    }
                                })
                            }.show(supportFragmentManager, "confirm")
                        }
                    })
                }
                layoutManager = FlexboxLayoutManager(context)

            }

            cameraResultItemAlcoholRv.apply {
                adapter = CameraResultItemRVAdapter().apply {
                    setCustomListener(object : CameraResultItemRVAdapter.ItemListener {
                        override fun onDelete(item: String, position: Int) {
                            ConfirmDialog("아이템 삭제", "해당 $item 아이템을 삭제하겠습니까?").apply {
                                setCustomListener(object: ConfirmDialog.DialogConfirmListener{
                                    override fun onConfirm() {
                                        dismiss()
                                        removeItem(position)
                                        Snackbar.make(binding.root, "삭제가 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                                    }
                                })
                            }.show(supportFragmentManager, "confirm")
                        }
                    })
                }
                layoutManager = FlexboxLayoutManager(context)
            }
        }

        initObserver()
    }

    private fun initObserver(){
        viewModel.alcoholLiveData.observe(this, Observer {
            (binding.cameraResultItemAlcoholRv.adapter as CameraResultItemRVAdapter).setData(it)
        })

        viewModel.foodLiveData.observe(this, Observer {
            (binding.cameraResultItemFoodRv.adapter as CameraResultItemRVAdapter).setData(it)
        })
    }

}