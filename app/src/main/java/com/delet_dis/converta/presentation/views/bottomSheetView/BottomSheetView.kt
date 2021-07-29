package com.delet_dis.converta.presentation.views.bottomSheetView

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.databinding.ViewBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetView : BottomSheetDialogFragment() {
    private lateinit var binding: ViewBottomSheetBinding

    private lateinit var parentFragmentCallback: ParentFragmentCallback

    private lateinit var currentAction: BottomSheetActionType

    private lateinit var submitButtonOnClickListener: () -> Unit

    private lateinit var editTextContent: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = ViewBottomSheetBinding.inflate(layoutInflater)

            parentFragmentCallback = context as ParentFragmentCallback

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewParameters()
    }

    private fun initViewParameters() {
        with(binding) {
            discardButton.setOnClickListener {
                this@BottomSheetView.dismiss()
            }

            root.setOnClickListener {
                this@BottomSheetView.dismiss()
            }

            bottomSheetDialogCard.setOnClickListener {
            }

            editText.doOnPreDraw {
                editText.setText(editTextContent)
            }

            currentMode.text = currentAction.actionStringId?.let { requireContext().getString(it) }

            submitButton.setOnClickListener {
                submitButtonOnClickListener.invoke()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        binding.editText.text.clear()
    }

    fun setUpBottomSheetInCategoryAddingMode(
        action: BottomSheetActionType
    ) {
        setUpCurrentModeByAction(action)

        setSubmitButtonOnClickListener {
            parentFragmentCallback.returnDataFromCategoryAdding(getTextFromEditText())
            afterSubmitOnClickActions()
        }
    }

    fun setUpBottomSheetInCategoryEditingMode(action: BottomSheetActionType, category: Category) {
        setUpCurrentModeByAction(action)
        category.name?.let { setEditTextContent(it) }

        setSubmitButtonOnClickListener {
            parentFragmentCallback.returnDataFromCategoryEditing(
                category,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }
    }

    fun setUpBottomSheetInPhraseAddingMode(action: BottomSheetActionType, categoryToAdd: Category) {
        setUpCurrentModeByAction(action)

        setSubmitButtonOnClickListener {
            parentFragmentCallback.returnDataFromPhraseAdding(
                categoryToAdd,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }
    }

    fun setUpBottomSheetInPhraseEditingMode(
        action: BottomSheetActionType,
        category: Category,
        phrase: Phrase
    ) {
        setUpCurrentModeByAction(action)
        phrase.name?.let { setEditTextContent(it) }

        setSubmitButtonOnClickListener {
            parentFragmentCallback.returnDataFromPhraseEditing(
                category,
                phrase,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }

    }

    private fun setSubmitButtonOnClickListener(function: () -> Unit) {
        submitButtonOnClickListener = function
    }

    private fun setUpCurrentModeByAction(action: BottomSheetActionType) {
        currentAction = action
    }

    private fun setEditTextContent(string: String) {
        editTextContent = string
    }

    private fun getTextFromEditText() = with(binding) {
        editText.text.toString()
    }

    private fun afterSubmitOnClickActions() = with(binding) {
        dismiss()
        editText.text.clear()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }

    interface ParentFragmentCallback {
        fun returnDataFromCategoryAdding(newCategoryName: String)
        fun returnDataFromCategoryEditing(category: Category, newCategoryName: String)
        fun returnDataFromPhraseAdding(categoryToAdd: Category, newPhraseName: String)
        fun returnDataFromPhraseEditing(category: Category, phrase: Phrase, newPhraseName: String)
    }
}