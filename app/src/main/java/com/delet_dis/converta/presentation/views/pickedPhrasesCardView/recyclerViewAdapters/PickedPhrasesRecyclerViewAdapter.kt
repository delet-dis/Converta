package com.delet_dis.converta.presentation.views.pickedPhrasesCardView.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.databinding.RecyclerViewPickedPhrasesListItemBinding

class PickedPhrasesRecyclerViewAdapter(
    private val values: MutableList<Phrase>,
    val clickListener: (Phrase) -> Unit
) : RecyclerView.Adapter<PickedPhrasesRecyclerViewAdapter.PhrasesHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhrasesHolder {
        return PhrasesHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_view_picked_phrases_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PickedPhrasesRecyclerViewAdapter.PhrasesHolder,
        position: Int
    ) {
        with(values[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class PhrasesHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RecyclerViewPickedPhrasesListItemBinding =
            RecyclerViewPickedPhrasesListItemBinding.bind(view)

        fun bind(phrase: Phrase) = with(binding) {
            itemText.text = phrase.name

            itemCard.setOnClickListener {
                clickListener.invoke(phrase)
            }
        }
    }
}