package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.recyclerViewAdapters


//class PhrasesPickingAdapter(
//    private val values: MutableList<Phrase>,
//    val clickListenerForPhrase: (Category) -> Unit,
//    val clickListenerForAddButton: (BottomSheetActionType) -> Unit
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
//        when (viewType) {
//            0 ->
//                AddButtonHolder(
//                    LayoutInflater.from(parent.context)
//                        .inflate(
//                            R.layout.view_add_button,
//                            parent,
//                            false
//                        )
//                )
//
//            else -> CategoriesHolder(
//                LayoutInflater.from(parent.context)
//                    .inflate(
//                        R.layout.recycler_view_bottom_list_item,
//                        parent,
//                        false
//                    )
//            )
//        }
//
//    override fun getItemCount(): Int = values.size + 1
//
//    override fun getItemViewType(position: Int): Int =
//        when (position) {
//            0 -> 0
//            else -> 1
//        }
//
//    inner class CategoriesHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        fun bindCategory(data: Category) = with(
//            RecyclerViewBottomListItemBinding.bind(view)
//        ) {
//            itemCard.setOnClickListener {
//                clickListenerForCategory(data)
//            }
//
//            categoryText.text = data.name
//        }
//    }
//
//    inner class AddButtonHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        fun bindAddButton() = with(ViewAddButtonBinding.bind(view)) {
//            addButton.setOnClickListener {
//                clickListenerForAddButton(BottomSheetActionType.CATEGORY_ADDING)
//            }
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is AddButtonHolder -> holder.bindAddButton()
//            is CategoriesHolder -> holder.bindCategory(values[position - 1])
//        }
//    }
//}