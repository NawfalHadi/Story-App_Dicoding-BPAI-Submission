package com.thatnawfal.storyappdicoding.presentation.main.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.thatnawfal.storyappdicoding.databinding.LayoutCameraxBinding
import com.thatnawfal.storyappdicoding.utils.createFile

class CameraFragment : Fragment() {

    private lateinit var binding: LayoutCameraxBinding

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutCameraxBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnCapture.setOnClickListener { captureImage() }
            btnSwitch.setOnClickListener { switchCamera() }
        }
    }

//    override fun onResume() {
//        super.onResume()
//    }

    private fun switchCamera() {
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
        else CameraSelector.DEFAULT_BACK_CAMERA

        startCamera()
    }

    private fun startCamera() {
        val cameraProvideFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProvideFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProvideFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.ivPreview.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e : Exception){
                Toast.makeText(requireContext(), "Camera Error", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun captureImage() {
        val imageCapture = imageCapture ?: return

        val photoFile = createFile(activity?.application!!)
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(requireContext(), "Captured", Toast.LENGTH_SHORT).show()

                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        PreviewFragment.CAMERA_X_RESULT, photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

}