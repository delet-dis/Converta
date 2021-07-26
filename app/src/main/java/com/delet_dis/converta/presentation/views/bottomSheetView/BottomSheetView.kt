package com.delet_dis.converta.presentation.views.bottomSheetView

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delet_dis.converta.R
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.databinding.ViewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetView : BottomSheetDialogFragment() {
    private lateinit var binding: ViewBottomSheetBinding

    var actionType = BottomSheetActionType.CATEGORY_ADDING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = ViewBottomSheetBinding.inflate(layoutInflater)

            initViewParameters()

            binding.root
        } else {
            view
        }
    }

    private fun initViewParameters() {
        with(binding) {
            discardButton.setOnClickListener {
                this@BottomSheetView.dismiss()
            }
            root.setOnClickListener {
                this@BottomSheetView.dismiss()
            }

            currentMode.text = requireContext().getString(actionType.actionStringId)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }
}