package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.databinding.RecyclerViewAddButtonBinding
import com.delet_dis.converta.databinding.RecyclerViewBottomListItemBinding


class CategoriesPickingAdapter(
    private val values: MutableList<Category>,
    val clickListenerForCategory: (Category) -> Unit,
    val longClickListenerForCategory: (BottomSheetActionType, Category) -> Unit,
    val clickListenerForAddButton: (BottomSheetActionType) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> AddButtonHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.recycler_view_add_button,
                            parent,
                            false
                        )
                )

            else -> CategoriesHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.recycler_view_bottom_list_item,
                        parent,
                        false
                    )
            )
        }

    override fun getItemCount(): Int = values.size + 1

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> 0
            else -> 1
        }

    private inner class CategoriesHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindCategory(category: Category) = with(
            RecyclerViewBottomListItemBinding.bind(view)
        ) {
            itemCard.setOnClickListener {
                clickListenerForCategory(category)
            }

            itemCard.setOnLongClickListener {
                longClickListenerForCategory(BottomSheetActionType.CATEGORY_EDITING, category)
                true
            }

            itemText.text = category.name
        }
    }

    private inner class AddButtonHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindAddButton() = with(RecyclerViewAddButtonBinding.bind(view)) {
            addButton.setOnClickListener {
                clickListenerForAddButton(BottomSheetActionType.CATEGORY_ADDING)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddButtonHolder -> holder.bindAddButton()
            is CategoriesHolder -> holder.bindCategory(values[position - 1])
        }
    }
}