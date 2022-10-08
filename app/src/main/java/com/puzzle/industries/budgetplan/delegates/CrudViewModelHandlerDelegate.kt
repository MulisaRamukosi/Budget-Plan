package com.puzzle.industries.budgetplan.delegates

import com.puzzle.industries.domain.common.response.Response
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

interface CrudViewModelHandlerDelegate<R, T> {

    val items: MutableStateFlow<List<T>>
    val crudResponseEventEmitter: MutableSharedFlow<Response<R>>
    val updateValueEventEmitter: MutableSharedFlow<Unit>
    val deleteValueEventEmitter: MutableSharedFlow<T>
    val onDeleteValue: (T) -> Unit
    val onUpdateValue: () -> Unit
}