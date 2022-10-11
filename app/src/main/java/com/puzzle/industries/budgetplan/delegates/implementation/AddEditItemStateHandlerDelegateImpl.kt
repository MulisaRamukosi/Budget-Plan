package com.puzzle.industries.budgetplan.delegates.implementation

import androidx.lifecycle.SavedStateHandle
import com.puzzle.industries.budgetplan.delegates.AddEditItemStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.models.KeyStateFlowHandler
import com.puzzle.industries.budgetplan.util.configs.EditConfig
import com.puzzle.industries.budgetplan.util.configs.ValidationConfig

class AddEditItemStateHandlerDelegateImpl(savedStateHandle: SavedStateHandle) :
    AddEditItemStateHandlerDelegate,
    SavedStateHandlerDelegate by SavedStateHandlerDelegateImpl(savedStateHandle) {

    override fun getRequiredInputsStateHandler(initialValue: Boolean): KeyStateFlowHandler<Boolean> {
        return registerKeyStateFlowHandler(
            key = ValidationConfig.VALIDATION_KEY,
            initialValue = initialValue
        )
    }

    override fun getIsUpdatingConditionStateHandler(initialValue: Boolean): KeyStateFlowHandler<Boolean> {
        return registerKeyStateFlowHandler(
            key = EditConfig.UPDATE_KEY,
            initialValue = initialValue
        )
    }

    override fun getActionReasonStateHandler(): KeyStateFlowHandler<String> {
        return registerKeyStateFlowHandler(
            key = EditConfig.ACTION_KEY,
            initialValue = ""
        )
    }


}