package com.delet_dis.converta.presentation.activities.mainActivity.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.databinding.RecyclerViewItemDatabaseRecordingsListBinding

class PhrasesPickingAdapter(
    private val values: MutableList<Phrase>,
    val clickListener: (Phrase) -> Unit
) : RecyclerView.Adapter<PhrasesPickingAdapter.PhrasesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhrasesHolder {
        return PhrasesHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.recycler_view_item_database_recordings_list,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: PhrasesHolder, position: Int) {
        with(values[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class PhrasesHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RecyclerViewItemDatabaseRecordingsListBinding =
            RecyclerViewItemDatabaseRecordingsListBinding.bind(view)

        fun bind(data: Phrase) = with(binding) {
            itemCard.setOnClickListener {
                clickListener(data)
            }

            categoryText.text = data.name
        }
    }
}