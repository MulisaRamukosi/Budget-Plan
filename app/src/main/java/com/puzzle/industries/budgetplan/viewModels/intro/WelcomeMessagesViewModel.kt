package com.puzzle.industries.budgetplan.viewModels.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.data.WelcomeMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeMessagesViewModel @Inject constructor(): ViewModel() {

    private val welcomeMessages: List<WelcomeMessage> = listOf(
        WelcomeMessage(
            vectorId = R.drawable.ic_welcome,
            titleId = R.string.welcome,
            messageId = R.string.about_budget_plan_app_summary
        ),
        WelcomeMessage(
            vectorId = R.drawable.ic_manage,
            titleId = R.string.app_name,
            messageId = R.string.about_budget_plan_features
        ),
        WelcomeMessage(
            vectorId = R.drawable.ic_track,
            titleId = R.string.usage_tracking,
            messageId = R.string.about_usage_tracking
        ),
        WelcomeMessage(
            vectorId = R.drawable.ic_reminder,
            titleId = R.string.reminder,
            messageId = R.string.about_reminder
        ),
        WelcomeMessage(
            vectorId = R.drawable.ic_setup,
            titleId = R.string.setup,
            messageId = R.string.about_setup
        )
    )

    private val currentPage: MutableLiveData<Int> by lazy { MutableLiveData(0) }

    fun getCurrentPage(): LiveData<Int> {
        return currentPage
    }

    fun getCurrentWelcomeMessage(): WelcomeMessage {
        return welcomeMessages[currentPage.value!!]
    }

    fun getNumOfPages(): Int {
        return welcomeMessages.size
    }

    fun navigateToNextMessage(): Boolean {
        val index = currentPage.value ?: 0
        if (index + 1 < welcomeMessages.size) {
            currentPage.value = index + 1
            return true
        }
        return false
    }
}