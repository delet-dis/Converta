package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase
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
                    Toast.makeText(requireContext(), category.name, Toast.LENGTH_SHORT).show()
                },
                { action, category ->
                    parentActivityCallback.displayBottomSheetForCategoryEditing(action, category)
                },
                { action ->
                    parentActivityCallback.displayBottomSheetForCategoryAdding(action)
                })
        })
    }

    override fun getFragmentId(): Int {
        return R.id.TTSFragment
    }

    interface ParentActivityCallback {
        fun displayBottomSheetForCategoryAdding(action: BottomSheetActionType)
        fun displayBottomSheetForCategoryEditing(action: BottomSheetActionType, category: Category)
        fun displayBottomSheetForPhraseAdding(action: BottomSheetActionType, category: Category)
        fun displayBottomSheetForPhraseEditing(
            action: BottomSheetActionType,
            category: Category,
            phrase: Phrase
        )
    }
}