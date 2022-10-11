package com.puzzle.industries.budgetplan.delegates

import com.puzzle.industries.budgetplan.models.KeyStateFlowHandler

interface AddEditItemStateHandlerDelegate {
    fun getRequiredInputsStateHandler(initialValue: Boolean): KeyStateFlowHandler<Boolean>
    fun getIsUpdatingConditionStateHandler(initialValue: Boolean): KeyStateFlowHandler<Boolean>
    fun getActionReasonStateHandler(): KeyStateFlowHandler<String>
}