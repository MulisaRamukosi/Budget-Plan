package com.puzzle.industries.budgetplan.viewModels.intro

import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.domain.datastores.AuthDataStore
import com.puzzle.industries.domain.datastores.LaunchTrackingDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val launchTrackingDataStore: LaunchTrackingDataStore,
    private val authDataStore: AuthDataStore
    ) : ViewModel(),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl(){

    var isFirstTimeLaunch: Boolean = true
    var alreadyAuthed: Boolean = false

    init {
        runCoroutine {
            launchTrackingDataStore.isFirstTimeLaunch().collectLatest { value ->
                isFirstTimeLaunch = value
            }
        }

        runCoroutine {
            authDataStore.isAuthed().collectLatest { value ->
                alreadyAuthed = value
            }
        }
    }

}