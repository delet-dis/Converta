package com.delet_dis.converta.presentation.views.pickedPhrasesCardView.recyclerViewAdapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.databinding.RecyclerViewNewPhraseListItemBinding
import com.delet_dis.converta.databinding.RecyclerViewPickedPhrasesListItemBinding

class PickedPhrasesRecyclerViewAdapter(
    private val values: MutableList<Phrase>,
    val clickListener: (Phrase) -> Unit,
    val addToPickedPhrasesFunction: (Phrase) -> Unit
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
                clickListener.invoke(phrase)
            }
        }
    }

    private inner class NewPhraseHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RecyclerViewNewPhraseListItemBinding =
            RecyclerViewNewPhraseListItemBinding.bind(view)

        fun bind() = with(binding) {
            itemText.setOnFocusChangeListener { v, hasFocus ->
                itemCard.apply {
                    alpha = if (hasFocus) {
                        1f
                    } else {
                        0.8f
                    }
                }
            }

            itemText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().contains(" ")) {
                        Toast.makeText(
                            binding.root.context,
                            s?.split(" ")?.get(0),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }
            )
        }
    }
}