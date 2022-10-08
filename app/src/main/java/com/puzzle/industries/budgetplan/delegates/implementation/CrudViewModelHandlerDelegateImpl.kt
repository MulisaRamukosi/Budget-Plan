package com.puzzle.industries.budgetplan.delegates.implementation

import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CrudViewModelHandlerDelegate
import com.puzzle.industries.domain.common.response.Response
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class CrudViewModelHandlerDelegateImpl<R, T> : CrudViewModelHandlerDelegate<R, T>,
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    private val _values by lazy {
        MutableStateFlow<List<T>>(value = emptyList())
    }

    private val _crudResponseEventEmitter by lazy {
        MutableSharedFlow<Response<R>>()
    }

    private val _deleteValueEventEmitter by lazy {
        MutableSharedFlow<T>()
    }

    private val _updateValueEventEmitter by lazy {
        MutableSharedFlow<Unit>()
    }

    override val crudResponseEventEmitter: MutableSharedFlow<Response<R>>
        get() = _crudResponseEventEmitter

    override val items: MutableStateFlow<List<T>>
        get() = _values

    override val updateValueEventEmitter: MutableSharedFlow<Unit>
        get() = _updateValueEventEmitter

    override val deleteValueEventEmitter: MutableSharedFlow<T>
        get() = _deleteValueEventEmitter

    override val onDeleteValue: (T) -> Unit
        get() = {
            runCoroutine {
                _deleteValueEventEmitter.emit(value = it)
            }
        }

    override val onUpdateValue: () -> Unit
        get() = {
            runCoroutine {
                updateValueEventEmitter.emit(value = Unit)
            }
        }

}