package com.kud.hanzan.ui.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.util.Rational
import android.util.TypedValue
import android.view.ScaleGestureDetector
import android.view.Surface
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentCameraBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit
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

    // Todo : Firebase Functions
//    private lateinit var firebaseFunctions : FirebaseFunctions
//    private lateinit var auth: FirebaseAuth

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
//        firebaseFunctions = Firebase.functions("asia-northeast3")
//        firebaseFunctions.useEmulator("10.0.2.2", 5001)
//
//        authCheck()

        setAnimationListener()
        initListener()
        observe()
    }

    // Todo : 임시 Auth 체크 함수
//    private fun authCheck(){
//        auth = Firebase.auth
//
//        val currentUser = auth.currentUser
//        if (currentUser == null){
//            auth.createUserWithEmailAndPassword("nerw173@gmail.com", "12345678")
//                .addOnCompleteListener(requireActivity()) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d("firebaseAuth", "createUserWithEmail:success")
//                        val user = auth.currentUser
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w("firebaseAuthFailure", "createUserWithEmail:failure", task.exception)
//                    }
//                }
//        }
//    }
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

                val useCaseGroup = UseCaseGroup.Builder()
                    .addUseCase(preview)
                    .addUseCase(imageCapture)
                    .build()
                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    requireActivity(), cameraSelector, useCaseGroup
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
                        val targetWidth = binding.cameraCropLayout.width
                        val targetHeight = binding.cameraCropLayout.height

                        val croppedBitmap = Bitmap.createBitmap(it, dpToPx(30), dpToPx(40), targetWidth, targetHeight )
                        recognizer.process(InputImage.fromBitmap(croppedBitmap, 0))
                            .addOnSuccessListener { visionText ->
                                cameraItemList.clear()
                                val resultText = visionText.textBlocks
                                resultText.forEach { e ->
                                    if(e.text.length >= 2) cameraItemList.add(e.text)
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

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), requireContext().resources.displayMetrics).toInt()
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

//    // Todo : Firebase ML
//    private fun processTextVision(originBitmap: Bitmap) {
//        val bitmap = scaleBitmapDown(originBitmap, 640)
//
//        // Convert bitmap to base64 encoded string
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
//        val base64encoded = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
//
//        // Create json request to cloud vision
//        val request = JsonObject()
//        // Add image to request
//        val image = JsonObject()
//        image.add("content", JsonPrimitive(base64encoded))
//        request.add("image", image)
//        //Add features to the request
//        val feature = JsonObject()
//        feature.add("type", JsonPrimitive("TEXT_DETECTION"))
//        // Alternatively, for DOCUMENT_TEXT_DETECTION:
//        // feature.add("type", JsonPrimitive("DOCUMENT_TEXT_DETECTION"))
//        val features = JsonArray()
//        features.add(feature)
//        request.add("features", features)
//
//        // 언어 설정 추가
//        val imageContext = JsonObject()
//        val languageHints = JsonArray()
//        languageHints.add("ko")
//        imageContext.add("languageHints", languageHints)
//        request.add("imageContext", imageContext)
//
//        annotateImage(request.toString())
//            .addOnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    // Task failed with an exception
//                    // ...
//                    Log.e("firebaseTest", "이미지 인식 실패 ${task.exception}")
//                } else {
//                    val annotation =
//                        task.result!!.asJsonArray[0].asJsonObject["fullTextAnnotation"].asJsonObject
//                    var pageText = ""
//                    for (page in annotation["pages"].asJsonArray) {
//                        for (block in page.asJsonObject["blocks"].asJsonArray) {
//                            var blockText = ""
//                            for (para in block.asJsonObject["paragraphs"].asJsonArray) {
//                                var paraText = ""
//                                for (word in para.asJsonObject["words"].asJsonArray) {
//                                    var wordText = ""
//                                    for (symbol in word.asJsonObject["symbols"].asJsonArray) {
//                                        wordText += symbol.asJsonObject["text"].asString
//                                        System.out.format(
//                                            "Symbol text: %s (confidence: %f)%n",
//                                            symbol.asJsonObject["text"].asString,
//                                            symbol.asJsonObject["confidence"].asFloat
//                                        )
//                                    }
//                                    System.out.format(
//                                        "Word text: %s (confidence: %f)%n%n", wordText,
//                                        word.asJsonObject["confidence"].asFloat
//                                    )
//                                    System.out.format(
//                                        "Word bounding box: %s%n",
//                                        word.asJsonObject["boundingBox"]
//                                    )
//                                    paraText = String.format("%s%s ", paraText, wordText)
//                                }
//                                System.out.format("%nParagraph: %n%s%n", paraText)
//                                System.out.format(
//                                    "Paragraph bounding box: %s%n",
//                                    para.asJsonObject["boundingBox"]
//                                )
//                                System.out.format(
//                                    "Paragraph Confidence: %f%n",
//                                    para.asJsonObject["confidence"].asFloat
//                                )
//                                blockText += paraText
//                            }
//                            pageText += blockText
//                        }
//                    }
//                    Log.e("firebaseTest", pageText)
//                }
//            }
//    }
//
//    private fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
//        val originalWidth = bitmap.width
//        val originalHeight = bitmap.height
//        var resizedWidth = maxDimension
//        var resizedHeight = maxDimension
//        if (originalHeight > originalWidth) {
//            resizedHeight = maxDimension
//            resizedWidth =
//                (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
//        } else if (originalWidth > originalHeight) {
//            resizedWidth = maxDimension
//            resizedHeight =
//                (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
//        } else if (originalHeight == originalWidth) {
//            resizedHeight = maxDimension
//            resizedWidth = maxDimension
//        }
//        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
//    }
//
//    private fun annotateImage(requestJson: String): Task<JsonElement> {
//        return firebaseFunctions
//            .getHttpsCallable("annotateImage")
//            .call(requestJson)
//            .continueWith { task ->
//                // This continuation runs on either success or failure, but if the task
//                // has failed then result will throw an Exception which will be
//                // propagated down.
//                val result = task.result?.data
//                JsonParser.parseString(Gson().toJson(result))
//            }
//    }
}