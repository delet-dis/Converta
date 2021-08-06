package com.delet_dis.converta.presentation.views.bottomSheetView

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.data.model.ColorModeType
import com.delet_dis.converta.data.model.SettingsActionType
import com.delet_dis.converta.databinding.ViewBottomSheetBinding
import com.delet_dis.converta.presentation.views.bottomSheetView.recyclerViewAdapters.SettingsListRecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetView : BottomSheetDialogFragment() {
    private lateinit var binding: ViewBottomSheetBinding

    private lateinit var parentActivityCallback: ParentActivityCallback

    private lateinit var submitButtonOnClickListener: () -> Unit

    private var currentAction: BottomSheetActionType? = null

    private var editTextContent: String? = null

    private var isDeleteButtonVisible: Boolean? = null

    private var deleteButtonOnClickListener: (() -> Unit)? = null

    private var arrayOfSettingsActions: Array<SettingsActionType>? = null

    private var currentColor: ColorModeType? = null

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

            currentMode.text = currentAction?.actionStringId?.let { requireContext().getString(it) }

            currentAction?.let {
                if (it == BottomSheetActionType.DISPLAYING_SETTINGS) {
                    showSettingsElements()
                    initRecyclerView()
                    initRecyclerViewAdapter()
                } else {
                    showPhrasesElements()
                }
            }

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

            when (currentColor) {
                ColorModeType.BLUE -> binding.discardButton.setImageDrawable(context?.let {
                    ResourcesCompat.getDrawable(it.resources, R.drawable.ic_discard_blue, it.theme)
                })

                ColorModeType.ORANGE -> binding.discardButton.setImageDrawable(context?.let {
                    ResourcesCompat.getDrawable(
                        it.resources,
                        R.drawable.ic_discard_orange,
                        it.theme
                    )
                })
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
        currentAction = action

        submitButtonOnClickListener = {
            parentActivityCallback.returnDataFromCategoryAdding(getTextFromEditText())
            afterSubmitOnClickActions()
        }

        isDeleteButtonVisible = false
    }

    fun setUpBottomSheetInCategoryEditingMode(action: BottomSheetActionType, category: Category) {
        currentAction = action
        category.name?.let { editTextContent = it }

        submitButtonOnClickListener = {
            parentActivityCallback.returnDataFromCategoryEditing(
                category,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }

        isDeleteButtonVisible = true
        deleteButtonOnClickListener = { parentActivityCallback.returnCategoryForDeleting(category) }
    }

    fun setUpBottomSheetInPhraseAddingMode(action: BottomSheetActionType, categoryToAdd: Category) {
        currentAction = action

        submitButtonOnClickListener = {
            parentActivityCallback.returnDataFromPhraseAdding(
                categoryToAdd,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }

        isDeleteButtonVisible = false
    }

    fun setUpBottomSheetInPhraseEditingMode(
        action: BottomSheetActionType,
        category: Category,
        phrase: Phrase
    ) {
        currentAction = action
        phrase.name?.let { editTextContent = it }

        submitButtonOnClickListener = {
            parentActivityCallback.returnDataFromPhraseEditing(
                category,
                phrase,
                getTextFromEditText()
            )
            afterSubmitOnClickActions()
        }

        isDeleteButtonVisible = true

        deleteButtonOnClickListener = { parentActivityCallback.returnPhraseForDeleting(phrase) }

    }

    fun setUpBottomSheetInSettingsListMode(
        action: BottomSheetActionType,
        settingsList: Array<SettingsActionType>
    ) {
        currentAction = action

        isDeleteButtonVisible = false

        arrayOfSettingsActions = settingsList
    }

    fun setBottomSheetCurrentColor(currentColorModeType: ColorModeType) {
        currentColor = currentColorModeType
    }

    private fun initRecyclerView() {
        binding.settingsList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initRecyclerViewAdapter() {
        binding.settingsList.adapter = arrayOfSettingsActions?.let { list ->
            SettingsListRecyclerViewAdapter(list) {
                parentActivityCallback.returnSettingAction(it)
            }
        }
    }

    private fun showSettingsElements() = with(binding) {
        settingsList.visibility = View.VISIBLE

        submitButton.visibility = View.INVISIBLE
        editText.visibility = View.INVISIBLE
        borderView.visibility = View.INVISIBLE
    }

    private fun showPhrasesElements() = with(binding) {
        settingsList.visibility = View.INVISIBLE

        submitButton.visibility = View.VISIBLE
        editText.visibility = View.VISIBLE
        borderView.visibility = View.VISIBLE
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

        fun returnSettingAction(actionType: SettingsActionType)
    }
}