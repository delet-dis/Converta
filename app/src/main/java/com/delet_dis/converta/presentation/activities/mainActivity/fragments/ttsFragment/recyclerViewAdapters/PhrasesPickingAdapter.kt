package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.databinding.RecyclerViewBottomListItemBinding
import com.delet_dis.converta.databinding.ViewAddButtonBinding
import com.delet_dis.converta.databinding.ViewBackButtonBinding


class PhrasesPickingAdapter(
    private val values: MutableList<Phrase>,
    val clickListenerForPhrase: (BottomSheetActionType, Phrase) -> Unit,
    val longClickListenerForPhrase: (BottomSheetActionType, Phrase) -> Unit,
    val clickListenerForAddButton: (BottomSheetActionType) -> Unit,
    val clickListenerForBackButton: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> BackButtonHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.view_back_button,
                        parent,
                        false
                    )
            )
            1 -> AddButtonHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.view_add_button,
                        parent,
                        false
                    )
            )
            else -> PhrasesHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.recycler_view_bottom_list_item,
                        parent,
                        false
                    )
            )
        }

    override fun getItemCount(): Int = values.size + 2

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> 0
            1 -> 1
            else -> 2
        }

    private inner class BackButtonHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindBackButton() = with(ViewBackButtonBinding.bind(view)) {
            backButton.setOnClickListener {
                clickListenerForBackButton()
            }
        }
    }

    private inner class AddButtonHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindAddButton() = with(ViewAddButtonBinding.bind(view)) {
            addButton.setOnClickListener {
                clickListenerForAddButton(BottomSheetActionType.PHRASE_ADDING)
            }
        }
    }

    private inner class PhrasesHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindPhrase(phrase: Phrase) = with(
            RecyclerViewBottomListItemBinding.bind(view)
        ) {
            itemCard.setOnClickListener {
                clickListenerForPhrase(BottomSheetActionType.PHRASE_PICKING, phrase)
            }

            itemCard.setOnLongClickListener {
                longClickListenerForPhrase(BottomSheetActionType.PHRASE_EDITING, phrase)
                true
            }

            itemText.text = phrase.name
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BackButtonHolder -> holder.bindBackButton()
            is AddButtonHolder -> holder.bindAddButton()
            is PhrasesHolder -> holder.bindPhrase(values[position - 2])
        }
    }
}