package com.kud.hanzan.ui.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.video.OutputFileOptions
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentCameraBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class CameraFragment : BaseFragment<FragmentCameraBinding>(R.layout.fragment_camera){
    private val viewModel by viewModels<CameraViewModel>()
    // 텍스트 인식 결과
    private var cameraItemList = mutableListOf<String>()

    private lateinit var cameraSelector : CameraSelector
    private val cameraProviderFuture by lazy{
        ProcessCameraProvider.getInstance(requireContext())
    }
    private val imageCapture by lazy {
        ImageCapture.Builder()
            .setTargetRotation(view?.display?.rotation!!)
            .build()
    }

    private var camera: Camera? = null

    private lateinit var preview : Preview

    // 추가 recognizer
    private val recognizer by lazy {
        TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
    }

    // Camera Shutter Animation
    private lateinit var cameraAnimationListener: Animation.AnimationListener

    // Todo : 임시
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    // 촬영 결과 화면을 다녀온 경우
    private var drinkList = ArrayList<String>()
    private var foodList = ArrayList<String>()
    // toggle button mode
    private var drinkMode = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if(it.resultCode == Activity.RESULT_OK){
                // Todo : 해당 값들 저장하고 onDestroy 할 때 지우기?
                drinkList.clear()
                drinkList.addAll(it.data?.getStringArrayExtra("drinkList")?: emptyArray())

                foodList.clear()
                foodList.addAll(it.data?.getStringArrayExtra("foodList")?: emptyArray())

                drinkMode = it.data?.getBooleanExtra("drinkMode", true) ?: true
                if (drinkMode) binding.cameraModeToggleBtn.check(R.id.camera_mode_drink_btn)
                else binding.cameraModeToggleBtn.check(R.id.camera_mode_food_btn)
            }
        }
        setAnimationListener()
        initListener()
        observe()
    }

    private fun startCamera(){
        cameraProviderFuture.addListener({
            // Camera의 lifecycle 을 lifecycleowner에 bind 하기 위해 사용
            val cameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                }
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            setUpPinchToZoom()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    requireActivity(), cameraSelector, imageCapture, preview
                )

            } catch (exc: Exception) {
                Log.e("Camera", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    private fun initListener(){
        activity?.let {
            if (it is MainActivity){
                it.setListener(object : MainActivity.CameraListener{
                    override fun onCameraClick() {
                        takePicture()
                    }
                })
                it.fabCameraListener()
            }
        }

        binding.cameraModeToggleBtn.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    R.id.camera_mode_drink_btn -> drinkMode = true
                    R.id.camera_mode_food_btn -> drinkMode = false
                }
            }
        }
    }

    fun takePicture(){
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object: ImageCapture.OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    val animation =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.camera_shutter)
                    animation.setAnimationListener(cameraAnimationListener)
                    binding.cameraShutterFrame.apply {
                        setAnimation(animation)
                        visibility = View.VISIBLE
                        startAnimation(animation)
                    }

                    binding.cameraPreview.bitmap?.let {
                        recognizer.process(InputImage.fromBitmap(it, 0))
                            .addOnSuccessListener { visionText ->
                                cameraItemList.clear()
                                val resultText = visionText.textBlocks
                                resultText.forEach { e ->
                                    cameraItemList.add(e.text)
                                }
                                Log.e("camera ocr result", cameraItemList.toString())
                                image.close()
                                if (drinkMode){
                                    // Todo : 술 리스트로 넘기기
                                    viewModel.postCameraDrink(cameraItemList)
                                } else {
                                    // Todo : 음식 리스트로 넘기기
                                    foodList.add("닭발")
//                    putExtra("foodList", foodList.toTypedArray())
                                    foodList.clear()
                                }
                            }
                    }

                }
                override fun onError(exception: ImageCaptureException) {
                    Log.e("camera","촬영에 실패 했습니다",exception)
                }
            }
        )
    }

    private fun observe(){
        viewModel.cameraDrinkData.observe(viewLifecycleOwner){ d ->
            activityResultLauncher.launch(Intent(requireActivity(), CameraResultActivity::class.java).apply{
                drinkList.addAll(d)
                Log.e("camera ocr server", d.toString())
                putExtra("drinkList",drinkList.toTypedArray())
                putExtra("foodList",foodList.toTypedArray())
            })
            drinkList.clear()
            foodList.clear()
        }
    }

    private fun setAnimationListener(){
        cameraAnimationListener = object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.cameraShutterFrame.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        }
    }

    private fun setUpPinchToZoom() {
        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val currentZoomRatio: Float = camera?.cameraInfo?.zoomState?.value?.zoomRatio ?: 1F
                val delta = detector.scaleFactor
                camera?.cameraControl?.setZoomRatio(currentZoomRatio * delta)
                return true
            }
        }
        val scaleGestureDetector = ScaleGestureDetector(requireContext(), listener)
        binding.cameraPreview.setOnTouchListener { _, event ->
            binding.cameraPreview.post {
                scaleGestureDetector.onTouchEvent(event)
            }
            return@setOnTouchListener true
        }
    }
}