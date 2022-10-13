package com.kud.hanzan.ui.camera

import android.os.Bundle
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.View
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentCameraBinding
import com.kud.hanzan.utils.base.BaseFragment
import com.kud.hanzan.vision.findSimilarity
import com.kud.hanzan.vision.getTrimmedString

class CameraFragment : BaseFragment<FragmentCameraBinding>(R.layout.fragment_camera) {
    private lateinit var cameraSelector : CameraSelector
    private val cameraProviderFuture by lazy{
        ProcessCameraProvider.getInstance(requireContext())
    }
    private var camera: Camera? = null

    private lateinit var preview : Preview

    // 추가 recognizer
    private val recognizer by lazy {
        TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCamera()
        initListener()
        sampleGraphicOverlay()
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
                    this, cameraSelector, preview)

            } catch(exc: Exception) {
                Log.e("Camera", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun initListener(){
        with(binding){
            cameraTurnBtn.setOnClickListener {
                turnCamera(cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
            }
            cameraCaptureBtn.setOnClickListener {
                // Todo : 첫 글자 알아내기 -> 첫 글자에 따른 집단 -> 집단 각각 아이템에 대한 유사도 분석 -> 유사도 80 넘은 것 중 가장 높은걸로 판단
                val result = recognizer.process(InputImage.fromBitmap(cameraPreview.bitmap!!, 0))
                    .addOnSuccessListener { visionText ->
                        var text = ""
                        val resultText = visionText.textBlocks
                        for (element in resultText) {
                            val trimStr = getTrimmedString(element.text)
                            text += trimStr + "\n"
                            if (findSimilarity(trimStr, "Ferrari Perle") > 0.5){
                                Log.e("camera test result success", findSimilarity(trimStr, "Ferrari Perle").toString())
                            }
                        }
                        Log.e("camera result", text)
                        val action = CameraFragmentDirections.actionCameraFragmentToCameraResultFragment()
                        findNavController().navigate(action)
                    }

            }
            /* Todo : 토글 버튼 선택된거에 따라 nlp 인식 범위 어디로 할지 */
        }
    }

    private fun turnCamera(backCamera: Boolean){
        val cameraProvider = cameraProviderFuture.get()
        try {
            cameraProvider.unbindAll()
            cameraSelector = if (backCamera) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
            cameraProvider.bindToLifecycle(this, cameraSelector, preview)
        } catch(exc: Exception) {
            Log.e("Camera", "Use case binding failed", exc)
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

    private fun sampleGraphicOverlay(){
//        binding.cameraOverlay.add(TextRecognitionGraphic( binding.cameraOverlay, "test" as TextBlock, Rect()))
//        binding.cameraOverlay.postInvalidate()
    }

}