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

    private lateinit var parentActivityCallback: ParentActivityCallback

    private lateinit var currentAction: BottomSheetActionType

    private lateinit var submitButtonOnClickListener: () -> Unit

    private var editTextContent: String? = null

    private var isDeleteButtonVisible: Boolean? = null

    private var deleteButtonOnClickListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = ViewBottomSheetBinding.inflate(layoutInflater)

            parentActivityCallback = context as ParentActivityCallback

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

            editTextContent?.let { text ->
                editText.doOnPreDraw {
                    editText.setText(text)
                }
            }

            currentMode.text = currentAction.actionStringId?.let { requireContext().getString(it) }

            submitButton.setOnClickListener {
                submitButtonOnClickListener.invoke()
            }

            isDeleteButtonVisible?.let {
                deleteButton.visibility = if (it) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
            }

            deleteButtonOnClickListener?.let { onClick ->
                deleteButton.setOnClickListener {
                    onClick.invoke()
                    dismiss()
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        binding.editText.text.clear()
        editTextContent = null
    }

    fun setUpBottomSheetInCategoryAddingMode(
        action: BottomSheetActionType
    ) {
        setUpCurrentModeByAction(action)

        setSubmitButtonOnClickListener {
            parentActivityCallback.returnDataFromCategoryAdding(getTextFromEditText())
            afterSubmitOnClickActions()
        }

        isDeleteButtonVisible(false)
    }

    fun setUpBottomSheetInCategoryEditingMode(action: BottomSheetActionType, category: Category) {
        setUpCurrentModeByAction(action)
        category.name?.let { setEditTextContent(it) }

        setSubmitButtonOnClickListener {
            parentActivityCallback.returnDataFromCategoryEditing(
                category,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }

        isDeleteButtonVisible(true)
        setDeleteButtonOnClickListener {
            parentActivityCallback.returnCategoryForDeleting(category)
        }
    }

    fun setUpBottomSheetInPhraseAddingMode(action: BottomSheetActionType, categoryToAdd: Category) {
        setUpCurrentModeByAction(action)

        setSubmitButtonOnClickListener {
            parentActivityCallback.returnDataFromPhraseAdding(
                categoryToAdd,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }

        isDeleteButtonVisible(false)
    }

    fun setUpBottomSheetInPhraseEditingMode(
        action: BottomSheetActionType,
        category: Category,
        phrase: Phrase
    ) {
        setUpCurrentModeByAction(action)
        phrase.name?.let { setEditTextContent(it) }

        setSubmitButtonOnClickListener {
            parentActivityCallback.returnDataFromPhraseEditing(
                category,
                phrase,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }

        isDeleteButtonVisible(true)
        setDeleteButtonOnClickListener {
            parentActivityCallback.returnPhraseForDeleting(phrase)
        }
    }

    private fun isDeleteButtonVisible(isVisible: Boolean) {
        isDeleteButtonVisible = isVisible
    }

    private fun setDeleteButtonOnClickListener(function: () -> Unit) {
        deleteButtonOnClickListener = function
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

    private fun afterSubmitOnClickActions() {
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }

    interface ParentActivityCallback {
        fun returnDataFromCategoryAdding(newCategoryName: String)
        fun returnDataFromCategoryEditing(category: Category, newCategoryName: String)
        fun returnDataFromPhraseAdding(categoryToAdd: Category, newPhraseName: String)
        fun returnDataFromPhraseEditing(category: Category, phrase: Phrase, newPhraseName: String)
        fun returnCategoryForDeleting(category: Category)
        fun returnPhraseForDeleting(phrase: Phrase)
    }
}