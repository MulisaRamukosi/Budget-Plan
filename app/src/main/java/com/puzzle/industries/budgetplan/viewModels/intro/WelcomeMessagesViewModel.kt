package com.puzzle.industries.budgetplan.viewModels.intro

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.data.WelcomeMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _currentPage: MutableStateFlow<Int> by lazy { MutableStateFlow(value = 0) }
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    fun getCurrentWelcomeMessage(): WelcomeMessage {
        return welcomeMessages[_currentPage.value]
    }

    fun getNumOfPages(): Int {
        return welcomeMessages.size
    }

    fun navigateToNextMessage(): Boolean {
        val index = _currentPage.value
        if (index + 1 < welcomeMessages.size) {
            _currentPage.value = index + 1
            return true
        }
        return false
    }
}