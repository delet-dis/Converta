package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.interfaces.FragmentParentInterface
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.databinding.FragmentTtsBinding
import com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.recyclerViewAdapters.CategoriesPickingAdapter
import com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.viewModel.TTSFragmentViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class TTSFragment : Fragment(), FragmentParentInterface {
    private lateinit var binding: FragmentTtsBinding

    private lateinit var ttsFragmentViewModel: TTSFragmentViewModel

    private lateinit var parentActivityCallback: ParentActivityCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentTtsBinding.inflate(layoutInflater)

            ttsFragmentViewModel = TTSFragmentViewModel(requireActivity().application)

            binding.root
        } else {
            view
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        parentActivityCallback = context as ParentActivityCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomListRecycler()

        displayCategoriesRecordings()
    }

    private fun initBottomListRecycler() = with(binding) {
        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        itemsBottomRecycler.layoutManager = layoutManager
    }


    private fun displayCategoriesRecordings() {
        ttsFragmentViewModel.categoriesRecordingsLiveData.observe(viewLifecycleOwner, { list ->
            binding.itemsBottomRecycler.adapter = CategoriesPickingAdapter(list,
                { action, category ->
                    handleBottomRecyclerViewCallback(action, category)
                },
                { action ->
                    parentActivityCallback.displayBottomSheet(action, null)
                })
        })
    }

    private fun handleBottomRecyclerViewCallback(
        action: BottomSheetActionType,
        category: Category
    ) {
        when (action) {
            BottomSheetActionType.CATEGORY_EDITING -> parentActivityCallback.displayBottomSheet(
                action, category
            )

            BottomSheetActionType.CATEGORY_PICKING -> parentActivityCallback.displayPhrasesByCategory(
                category
            )
            else -> {
            }
        }
    }

    override fun getFragmentId(): Int {
        return R.id.TTSFragment
    }

    interface ParentActivityCallback {
        fun displayBottomSheet(action: BottomSheetActionType, category: Category?)
        fun displayPhrasesByCategory(category: Category)
    }
}