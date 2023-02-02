package com.thatnawfal.storyappdicoding.presentation.main.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.thatnawfal.storyappdicoding.R
import com.thatnawfal.storyappdicoding.databinding.FragmentPreviewBinding
import com.thatnawfal.storyappdicoding.utils.uriToFile
import java.io.File

class PreviewFragment : Fragment() {

    private lateinit var binding: FragmentPreviewBinding

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

        actionSetup()
    }

    private fun actionSetup() {
        with(binding){
            btnCamera.setOnClickListener {
                // using camera
            }

            btnGallery.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"

                val chooser = Intent.createChooser(intent, getString(R.string.choose_image))
                launcherGallery.launch(chooser)
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
}