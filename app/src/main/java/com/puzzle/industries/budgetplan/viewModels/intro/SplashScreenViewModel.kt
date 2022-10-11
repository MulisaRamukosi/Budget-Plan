package com.puzzle.industries.budgetplan.viewModels.intro

import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.domain.services.LaunchTrackingPreferenceService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val launchTrackingPreferenceService: LaunchTrackingPreferenceService) :
    ViewModel(),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl(){

    var isFirstTimeLaunch: Boolean = true

    init {
        runCoroutine {
            launchTrackingPreferenceService.isFirstTimeLaunch().collectLatest { value ->
                isFirstTimeLaunch = value
            }
        }
    }

}