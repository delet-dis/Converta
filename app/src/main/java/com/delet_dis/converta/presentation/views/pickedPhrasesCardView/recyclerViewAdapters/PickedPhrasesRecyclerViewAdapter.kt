package com.delet_dis.converta.presentation.views.pickedPhrasesCardView.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.databinding.RecyclerViewNewPhraseListItemBinding
import com.delet_dis.converta.databinding.RecyclerViewPickedPhrasesListItemBinding

class PickedPhrasesRecyclerViewAdapter(
    private val values: MutableList<Phrase>,
    val deletePhraseClickListener: (Phrase) -> Unit,
    val addPhraseClickListener: (Phrase) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            values.size -> NewPhraseHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.recycler_view_new_phrase_list_item,
                        parent,
                        false
                    )
            )
            else -> PhrasesHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.recycler_view_picked_phrases_list_item,
                        parent,
                        false
                    )
            )
        }

    override fun getItemCount(): Int = values.size + 1

    override fun getItemViewType(position: Int): Int =
        when (position) {
            values.size -> values.size
            else -> position
        }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is NewPhraseHolder -> holder.bind()
            is PhrasesHolder -> holder.bind(values[position])
        }
    }

    private inner class PhrasesHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RecyclerViewPickedPhrasesListItemBinding =
            RecyclerViewPickedPhrasesListItemBinding.bind(view)

        fun bind(phrase: Phrase) = with(binding) {
            itemText.text = phrase.name

            itemCard.setOnClickListener {
                deletePhraseClickListener.invoke(phrase)
            }
        }
    }

    private inner class NewPhraseHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RecyclerViewNewPhraseListItemBinding =
            RecyclerViewNewPhraseListItemBinding.bind(view)

        fun bind() {
            initOnFocusChangeListener()

            initOnDoneListener()
        }

        private fun initOnDoneListener() = with(binding) {
            itemText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    splitAndAddToPickedPhrasesString(itemText.text.toString())
                }

                itemText.clearFocus()

                true
            }
        }

        private fun initOnFocusChangeListener() = with(binding) {
            itemText.setOnFocusChangeListener { _, hasFocus ->
                itemCard.apply {
                    alpha = if (hasFocus) {
                        1f
                    } else {
                        0.8f
                    }
                }
            }
        }

        private fun splitAndAddToPickedPhrasesString(string: CharSequence?) {
            if (string.toString().contains(" ")) {
                val splittedString = string?.split(" ")

                splittedString?.forEach {
                    if (it.isNotEmpty()) {
                        if (it.contains(",") or it.contains(".")) {
                            if (it.contains(",")) {
                                addPhraseClickListener.invoke(Phrase(it.split(",")[0]))
                                addPhraseClickListener.invoke(Phrase(","))
                            }
                            if (it.contains(".")) {
                                addPhraseClickListener.invoke(Phrase(it.split(".")[0]))
                                addPhraseClickListener.invoke(Phrase("."))
                            }
                        } else {
                            addPhraseClickListener.invoke(Phrase(it))
                        }
                    }
                }
            } else if (!string.isNullOrEmpty()) {
                addPhraseClickListener.invoke(Phrase(string.toString()))
            }
        }
    }
}