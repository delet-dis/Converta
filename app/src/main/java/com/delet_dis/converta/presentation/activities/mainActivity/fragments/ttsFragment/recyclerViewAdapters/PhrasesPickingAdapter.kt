package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.databinding.RecyclerViewBottomListItemBinding
import com.delet_dis.converta.domain.extensions.dpToPx

class PhrasesPickingAdapter(
    private val values: MutableList<Category>,
    val clickListener: (Category) -> Unit
) : RecyclerView.Adapter<PhrasesPickingAdapter.PhrasesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhrasesHolder {
        return PhrasesHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.recycler_view_bottom_list_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: PhrasesHolder, position: Int) {
        with(values[position]) {
            holder.bind(this, position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class PhrasesHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RecyclerViewBottomListItemBinding =
            RecyclerViewBottomListItemBinding.bind(view)

        fun bind(data: Category, position: Int) = with(binding) {
            if (position == 0) {
                root.updatePadding(left = (61).dpToPx(binding.root.context).toInt())
            }

            itemCard.setOnClickListener {
                clickListener(data)
            }

            categoryText.text = data.name
        }
    }
}