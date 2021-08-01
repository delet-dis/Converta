package com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.database.entities.Phrase
import com.delet_dis.converta.data.interfaces.FragmentParentInterface
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.databinding.FragmentTtsBinding
import com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.recyclerViewAdapters.CategoriesPickingAdapter
import com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.recyclerViewAdapters.PhrasesPickingAdapter
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

        if (savedInstanceState == null) {
            initBottomListRecycler()

            displayCategoriesRecordings()

            initPickedPhrasesObserver()
        }
    }

    private fun initBottomListRecycler() = with(binding) {
        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        itemsBottomRecycler.layoutManager = layoutManager
    }

    private fun initPickedPhrasesObserver() = with(binding) {
        pickedPhrasesCardView.apply {
            deletePhraseFromListOfPicked = ::deletePhraseFromListOfPicked
            deleteAllPhrasesFromListOfPicked = { deleteAllPhrasesFromListOfPicked() }
            addPhraseToListOfPicked = ::addPhraseToListOfPicked
            submitPickedPhrases = { convertPickedPhrasesToSpeech() }
        }

        ttsFragmentViewModel.pickedPhrasesLiveData.observe(viewLifecycleOwner, {
            pickedPhrasesCardView.apply {
                pickedPhrases = it as ArrayList<Phrase>
            }
        })
    }

    private fun displayCategoriesRecordings() = with(binding) {
        currentBottomRecyclerDisplayingMode.text =
            requireContext().getString(R.string.phrasesCategoriesModeDisplayingText)

        ttsFragmentViewModel.categoriesRecordingsLiveData.observe(viewLifecycleOwner, { list ->
            requireActivity().runOnUiThread {
                itemsBottomRecycler.adapter = CategoriesPickingAdapter(list,
                    { category: Category ->
                        displayPhrasesRecordings(category)
                    },
                    { action, category ->
                        parentActivityCallback.displayBottomSheetForCategoryEditing(
                            action,
                            category
                        )
                    },
                    { action ->
                        parentActivityCallback.displayBottomSheetForCategoryAdding(action)
                    })

                itemsBottomRecycler.smoothScrollToPosition(0)
            }
        })
    }

    private fun displayPhrasesRecordings(pickedCategory: Category) {
        ttsFragmentViewModel.loadPhrasesRecordingsByCategory(pickedCategory)

        binding.currentBottomRecyclerDisplayingMode.text = pickedCategory.name

        ttsFragmentViewModel.phrasesInCategoryRecordingsLiveData.observe(viewLifecycleOwner,
            { list ->
                binding.itemsBottomRecycler.adapter = PhrasesPickingAdapter(list,
                    { phrase ->
                        ttsFragmentViewModel.addPickedPhraseRecording(phrase)
                    },
                    { action, phrase ->
                        parentActivityCallback.displayBottomSheetForPhraseEditing(
                            action,
                            pickedCategory,
                            phrase
                        )
                    },
                    { action ->
                        parentActivityCallback.displayBottomSheetForPhraseAdding(
                            action,
                            pickedCategory
                        )
                    },
                    {
                        displayCategoriesRecordings()
                    })

                binding.itemsBottomRecycler.smoothScrollToPosition(0)
            })
    }

    private fun deletePhraseFromListOfPicked(phrase: Phrase) =
        ttsFragmentViewModel.deletePhraseFromListOfPicked(phrase)

    private fun deleteAllPhrasesFromListOfPicked() =
        ttsFragmentViewModel.deleteAllPhrasesFromListOfPicked()

    private fun addPhraseToListOfPicked(phrase: Phrase) =
        ttsFragmentViewModel.addPhraseToListOfPicked(phrase)

    private fun convertPickedPhrasesToSpeech() =
        ttsFragmentViewModel.speakPickedPhrases()

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