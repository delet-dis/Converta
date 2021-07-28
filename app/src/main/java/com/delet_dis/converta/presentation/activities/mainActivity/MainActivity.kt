package com.delet_dis.converta.presentation.activities.mainActivity

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.delet_dis.converta.R
import com.delet_dis.converta.data.database.entities.Category
import com.delet_dis.converta.data.interfaces.FragmentParentInterface
import com.delet_dis.converta.data.model.ApplicationMainModeType
import com.delet_dis.converta.data.model.BottomSheetActionType
import com.delet_dis.converta.databinding.ActivityMainBinding
import com.delet_dis.converta.presentation.activities.mainActivity.fragments.ttsFragment.TTSFragment
import com.delet_dis.converta.presentation.activities.mainActivity.viewModel.MainActivityViewModel
import com.delet_dis.converta.presentation.views.bottomSheetView.BottomSheetView

class MainActivity : AppCompatActivity(), TTSFragment.ParentActivityCallback {
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private var hostFragment: Fragment? = null

    private lateinit var bottomSheetView: BottomSheetView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        hostFragment =
            supportFragmentManager
                .findFragmentById(binding.navigationMainControllerContainerView.id)

        mainActivityViewModel = MainActivityViewModel(application)

        bottomSheetView = BottomSheetView()

        setContentView(binding.root)

        initActivityBackground()

        initBottomNavigationViewOnClickListener()

        updatePreferredApplicationMode()

        initPreferredApplicationModeObserver()
    }


    private fun initPreferredApplicationModeObserver() =
        with(mainActivityViewModel.preferredApplicationMode) {
            observe(
                this@MainActivity, {
                    when (it.name) {
                        ApplicationMainModeType.TTS_MODE.name -> {
                            translationToOrangeState()
                            makeBottomNavigationViewActiveTTSButton()
                        }
                        ApplicationMainModeType.STT_MODE.name -> {
                            translationToBlueState()
                            makeBottomNavigationViewActiveSTTButton()
                        }
                    }
                }
            )
        }

    private fun updatePreferredApplicationMode() =
        mainActivityViewModel.getPreferredApplicationMode()


    private fun initBottomNavigationViewOnClickListener() = with(binding.bottomNavigationView) {
        setOnItemSelectedListener {
            when (it.itemId) {
                R.id.TTSButton -> {
                    if (!isTTSFragmentDisplaying()) {
                        displayTTSFragment()
                        translationToOrangeState()
                    }
                    true
                }
                R.id.STTButton -> {
                    if (!isSTTFragmentDisplaying()) {
                        displaySTTFragment()
                        translationToBlueState()
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun translationToBlueState(): Boolean {
        binding.rootView.transitionToState(R.id.backgroundImageInMainGoToBlue)

        return true
    }

    private fun translationToOrangeState(): Boolean {
        binding.rootView.transitionToState(R.id.backgroundImageInMainGoToOrange)

        return true
    }

    private fun makeBottomNavigationViewActiveSTTButton() {
        binding.bottomNavigationView.selectedItemId = R.id.STTButton
    }

    private fun makeBottomNavigationViewActiveTTSButton() {
        binding.bottomNavigationView.selectedItemId = R.id.TTSButton
    }

    private fun displayTTSFragment() =
        hostFragment?.findNavController()?.navigate(R.id.action_STTFragment_to_TTSFragment)

    private fun displaySTTFragment() =
        hostFragment?.findNavController()?.navigate(R.id.action_TTSFragment_to_STTFragment)

    private fun isTTSFragmentDisplaying(): Boolean {
        return (hostFragment
            ?.childFragmentManager
            ?.primaryNavigationFragment as FragmentParentInterface)
            .getFragmentId() == R.id.TTSFragment
    }

    private fun isSTTFragmentDisplaying(): Boolean {
        return (hostFragment
            ?.childFragmentManager
            ?.primaryNavigationFragment as FragmentParentInterface)
            .getFragmentId() == R.id.STTFragment
    }

    private fun initActivityBackground() = with(binding.backgroundImage) {
        background = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
                getColor(R.color.orange),
                getColor(R.color.gradientSubOrangeOne),
                getColor(R.color.gradientSubOrangeTwo),
                getColor(R.color.gradientSubBlueThree),
                getColor(R.color.gradientSubBlueFour),
                getColor(R.color.blue)
            )
        )
    }

    private fun showBottomSheet(actionType: BottomSheetActionType, category: Category?) {
        bottomSheetView.show(supportFragmentManager, null)
        bottomSheetView.actionType = actionType

        when (actionType) {
            BottomSheetActionType.CATEGORY_EDITING -> {
                bottomSheetView.currentCategory = category
                bottomSheetView.renameCategorySubmitButtonOnClickListener =
                    ::renameCategoryInDatabase
            }
            BottomSheetActionType.CATEGORY_ADDING -> {
                bottomSheetView.addCategorySubmitButtonOnClickListener = ::addCategoryToDatabase
            }
        }
    }

    private fun addCategoryToDatabase(category: String) {
        mainActivityViewModel.addCategoryToDatabase(category)
    }

    private fun renameCategoryInDatabase(category: Category, newCategoryName: String) {
        mainActivityViewModel.renameCategoryInDatabase(category, newCategoryName)
    }

    override fun displayBottomSheet(action: BottomSheetActionType, category: Category?) {
        showBottomSheet(action, category)
    }

    override fun displayPhrasesByCategory(category: Category) {
    }
}