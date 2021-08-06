package com.delet_dis.converta.presentation.views.bottomSheetView.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.delet_dis.converta.R
import com.delet_dis.converta.data.model.SettingsActionType
import com.delet_dis.converta.databinding.RecyclerViewSettingsListItemBinding

class SettingsListRecyclerViewAdapter(
    private val values: Array<SettingsActionType>,
    private val clickListener: (SettingsActionType) -> Unit
) : RecyclerView.Adapter<SettingsListRecyclerViewAdapter.SettingHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingHolder =
        SettingHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_view_settings_list_item, parent, false)
        )


    override fun onBindViewHolder(
        holder: SettingHolder,
        position: Int
    ) = with(values[position]) {
        holder.bind(this)
    }

    override fun getItemCount(): Int = values.size

    inner class SettingHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding: RecyclerViewSettingsListItemBinding =
            RecyclerViewSettingsListItemBinding.bind(view)

        fun bind(data: SettingsActionType) = with(binding) {
            itemCard.setOnClickListener {
                clickListener(data)
            }

            itemImage.setImageDrawable(AppCompatResources.getDrawable(view.context, data.imageId))

            itemText.text = view.context.getString(data.stringId)
        }
    }
}