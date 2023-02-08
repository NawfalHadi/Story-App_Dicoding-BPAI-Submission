package com.thatnawfal.storyappdicoding.presentation.main.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thatnawfal.storyappdicoding.R
import com.thatnawfal.storyappdicoding.databinding.FragmentPreviewBinding
import com.thatnawfal.storyappdicoding.presentation.auth.bussiness.AuthenticationViewModel
import com.thatnawfal.storyappdicoding.presentation.main.ui.bottomsheet.DescriptionBottomSheet
import com.thatnawfal.storyappdicoding.utils.rotateBitmap
import com.thatnawfal.storyappdicoding.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PreviewFragment : Fragment() {

    private lateinit var binding: FragmentPreviewBinding

    private val authViewModel: AuthenticationViewModel by viewModels()

    private var imageDesc : String? = null
    private var getFile: File? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            getFile = myFile
            binding.ivPreview.setImageURI(selectedImg)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Not Granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!allPermissionsGranted()) {
            permissionLauncher.launch(REQUIRED_PERMISSIONS[0])
        }

        dataSetup()
        actionSetup()
    }

    private fun dataSetup() {
        authViewModel.getSession().observe(viewLifecycleOwner){
            binding.tvNamesPreview.text = it.name
        }

        val cameraxResult: File? = findNavController().currentBackStackEntry?.savedStateHandle?.get<File>(
            CAMERA_X_RESULT)

        if (cameraxResult != null) {
            getFile = cameraxResult
            binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(cameraxResult.path))
        }
    }

    private fun actionSetup() {
        with(binding){
            btnCamera.setOnClickListener {
                findNavController().navigate(R.id.action_previewFragment_to_cameraFragment)
            }

            btnGallery.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"

                val chooser = Intent.createChooser(intent, getString(R.string.choose_image))
                launcherGallery.launch(chooser)
            }

            btnSetdesc.setOnClickListener {
                showsDescBottomSheet()
            }


            cardviewDetail.setOnClickListener {
                if (shadow.visibility == View.INVISIBLE) {
                    shadow.visibility = View.VISIBLE
                    btnCamera.visibility = View.VISIBLE
                    btnGallery.visibility = View.VISIBLE
                } else {
                    shadow.visibility = View.INVISIBLE
                    btnCamera.visibility = View.GONE
                    btnGallery.visibility = View.GONE
                }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {DescriptionBottomSheet::class.java.simpleName
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showsDescBottomSheet() {
        val currentDialog = parentFragmentManager.findFragmentByTag(DescriptionBottomSheet::class.java.simpleName)
        if (currentDialog == null){
            DescriptionBottomSheet(object : DescriptionBottomSheet.OnDescriptionChanged {
                override fun descriptionChanged(desc: String?) {
                    imageDesc = desc
                }
            }, imageDesc).show(parentFragmentManager, DescriptionBottomSheet::class.java.simpleName)
        }
    }

    companion object {
        const val CAMERA_X_RESULT = "CAMERA_X_RESULT"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}