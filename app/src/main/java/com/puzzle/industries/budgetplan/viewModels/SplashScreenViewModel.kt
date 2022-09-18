package com.puzzle.industries.budgetplan.viewModels

import androidx.lifecycle.ViewModel
import com.puzzle.industries.domain.services.launch.LaunchTrackingService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val launchTrackingService: LaunchTrackingService)
    : ViewModel() {

        fun isFirstTimeLaunch(): Boolean {
            return launchTrackingService.isFirstTimeLaunch()
        }
}