package com.kud.hanzan.ui.camera

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.ScaleGestureDetector
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.Text.TextBlock
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityCameraBinding
import com.kud.hanzan.utils.base.BaseActivity
import com.kud.hanzan.vision.GraphicOverlay
import com.kud.hanzan.vision.TextRecognitionGraphic
import com.kud.hanzan.vision.TextRecognitionProcessor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraActivity : BaseActivity<ActivityCameraBinding>(R.layout.activity_camera) {
    private lateinit var cameraSelector : CameraSelector
    private val cameraProviderFuture by lazy{
        ProcessCameraProvider.getInstance(this)
    }
    private var camera: Camera? = null

    private lateinit var preview : Preview

    // 추가 recognizer
    private val recognizer by lazy {
        TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
    }

    override fun initView() {
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

        }, ContextCompat.getMainExecutor(this))
    }

    private fun initListener(){
        with(binding){
            cameraTurnBtn.setOnClickListener {
                turnCamera(cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
            }
            cameraCaptureBtn.setOnClickListener {
                val result = recognizer.process(InputImage.fromBitmap(cameraPreview.bitmap!!, 0))
                    .addOnSuccessListener { visionText ->
                        var text = ""
                        val resultText = visionText.textBlocks
                        for (element in resultText){
                            text += element.text + "\n"
                        }
                        Log.e("camera", text)
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
        val scaleGestureDetector = ScaleGestureDetector(this, listener)
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