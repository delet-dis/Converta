package com.delet_dis.converta.presentation.views.addButtonView

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.delet_dis.converta.R
import com.delet_dis.converta.databinding.ViewAddButtonBinding

class AddButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: ViewAddButtonBinding

    init {
        inflate(
            context,
            R.layout.view_add_button,
            this
        ).also { view ->
            binding = ViewAddButtonBinding.bind(view)
        }
    }

    interface ParentActivityCallback {
        fun displayDialog()
    }
}