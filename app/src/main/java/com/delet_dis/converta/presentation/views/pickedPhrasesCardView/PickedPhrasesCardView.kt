package com.delet_dis.converta.presentation.views.pickedPhrasesCardView

import android.app.Application
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.databinding.ViewPickedPhrasesCardBinding
import com.delet_dis.converta.presentation.views.pickedPhrasesCardView.recyclerViewAdapters.PickedPhrasesRecyclerViewAdapter
import com.delet_dis.converta.presentation.views.pickedPhrasesCardView.viewModel.PickedPhrasesCardViewViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlin.reflect.KFunction1

class PickedPhrasesCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: ViewPickedPhrasesCardBinding

    private val pickedPhrasesCardViewViewModel: PickedPhrasesCardViewViewModel

    var deletePhraseFromListOfPicked: KFunction1<Phrase, Unit>? = null

    var deleteAllPhrasesFromListOfPicked: (() -> Unit)? = null

    var addPhraseToListOfPicked: KFunction1<Phrase, Unit>? = null

    var submitPickedPhrases: (() -> Unit)? = null

    var pickedPhrases = ArrayList<Phrase>()
        set(value) = setPickedPhrasesList(value)

    var isNewPhrasesHolderDisplayed: Boolean? = null
        set(value){
            field = value
            initRecyclerViewList(value)
        }

    init {
        inflate(context, R.layout.view_picked_phrases_card, this).also {
            binding = ViewPickedPhrasesCardBinding.bind(this)
        }

        pickedPhrasesCardViewViewModel =
            PickedPhrasesCardViewViewModel(context.applicationContext as Application)

        initRecyclerView()

        initSubmitButtonOnClickListener()
    }

    private fun initRecyclerView() = with(binding) {
        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        itemsRecyclerView.layoutManager = layoutManager
    }

    private fun setPickedPhrasesList(list: ArrayList<Phrase>) = with(binding) {
        itemsRecyclerView.adapter = isNewPhrasesHolderDisplayed?.let { value ->
            PickedPhrasesRecyclerViewAdapter(list,
                value,
                {
                    deletePhraseFromListOfPicked?.invoke(it)
                },
                {
                    addPhraseToListOfPicked?.invoke(it)
                }
            )
        }

        if (isNewPhrasesHolderDisplayed == true) {
            itemsRecyclerView.smoothScrollToPosition(
                (itemsRecyclerView.adapter
                        as PickedPhrasesRecyclerViewAdapter).itemCount - 1
            )
        }

        initDiscardButtonOnClickListener()

        if (list.size == 0) {
            hideControlElements()
        } else {
            showControlElements()
        }
    }

    private fun hideControlElements() = with(binding) {
        discardButton.visibility = View.INVISIBLE
        submitButton.visibility = View.INVISIBLE
    }

    private fun showControlElements() = with(binding) {
        discardButton.visibility = View.VISIBLE
        submitButton.visibility = View.VISIBLE
    }

    private fun initDiscardButtonOnClickListener() =
        with(binding) {
            discardButton.setOnClickListener {
                deleteAllPhrasesFromListOfPicked?.invoke()
            }
        }

    private fun initSubmitButtonOnClickListener() =
        with(binding) {
            submitButton.setOnClickListener {
                submitPickedPhrases?.invoke()
            }
        }

    fun showTTSLoading() = with(binding) {
        submitButton.visibility = View.INVISIBLE
        progressIndicator.setProgressCompat(0, false)
        progressIndicator.visibility = View.VISIBLE
        progressIndicator.setProgressCompat(75, true)
    }

    fun showTTSStart() = with(binding) {
        submitButton.visibility = View.INVISIBLE
        progressIndicator.setProgressCompat(100, true)
        stopButton.apply {
            visibility = View.VISIBLE

            setOnClickListener {
                pickedPhrasesCardViewViewModel.stopTTSEngine()
                showTTSDone()
            }
        }
        progressIndicator.visibility = View.INVISIBLE
    }

    fun showTTSDone() = with(binding) {
        stopButton.visibility = View.INVISIBLE
        submitButton.visibility = View.VISIBLE
    }

    private fun initRecyclerViewList(value: Boolean?) {
        if (value == true) {
            setPickedPhrasesList(ArrayList())
        }
    }
}