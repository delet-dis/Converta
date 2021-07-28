package com.delet_dis.converta.presentation.views.bottomSheetView

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.databinding.ViewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

class BottomSheetView : BottomSheetDialogFragment() {
    private lateinit var binding: ViewBottomSheetBinding

    var actionType = BottomSheetActionType.CATEGORY_ADDING

    var currentCategory: Category? = null

    var addCategorySubmitButtonOnClickListener:
            KFunction1<String, Unit>? = null

    var renameCategorySubmitButtonOnClickListener:
            KFunction2<Category, String, Unit>? = null

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

            currentCategory?.name?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                editText.text.clear()
                editText.setText(it)
            }

            submitButton.setOnClickListener {
                when (actionType) {
                    BottomSheetActionType.CATEGORY_ADDING -> {
                        addCategorySubmitButtonOnClickListener?.invoke(editText.text.toString())
                    }
                    BottomSheetActionType.CATEGORY_EDITING ->
                        currentCategory?.let { category ->
                            renameCategorySubmitButtonOnClickListener?.invoke(
                                category, editText.text.toString()
                            )
                        }
                }
                dismiss()
                editText.text.clear()
            }

            bottomSheetDialogCard.setOnClickListener {
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }
}