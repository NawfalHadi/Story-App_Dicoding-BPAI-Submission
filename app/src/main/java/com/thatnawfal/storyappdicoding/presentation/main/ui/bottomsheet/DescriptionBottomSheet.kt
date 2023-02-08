package com.thatnawfal.storyappdicoding.presentation.main.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thatnawfal.storyappdicoding.databinding.BottomsheetDescriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionBottomSheet(
    private val listener : OnDescriptionChanged,
    private val desc: String? = null
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetDescriptionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingData()
    }

    private fun bindingData() {
        with(binding){
            etDesc.setText(desc)

            btnSubmit.setOnClickListener {
                listener.descriptionChanged(etDesc.text.toString())
                dismiss()
            }
        }
    }

    interface OnDescriptionChanged{
        fun descriptionChanged(desc: String?)
    }
}