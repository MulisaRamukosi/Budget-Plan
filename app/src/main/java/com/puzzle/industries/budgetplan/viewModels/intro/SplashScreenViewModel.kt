package com.puzzle.industries.budgetplan.viewModels.intro

import androidx.lifecycle.ViewModel
import com.puzzle.industries.domain.services.LaunchTrackingPreferenceService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val launchTrackingPreferenceService: LaunchTrackingPreferenceService) :
    ViewModel() {

    fun isFirstTimeLaunch(): Boolean {
        return launchTrackingPreferenceService.isFirstTimeLaunch()
    }
}