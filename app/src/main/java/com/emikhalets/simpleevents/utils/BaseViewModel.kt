package com.emikhalets.simpleevents.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface AppState
interface AppAction

abstract class BaseViewModel<S : AppState, A : AppAction> : ViewModel() {

    private val initialState: S by lazy { createInitialState() }

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state get() = _state.asStateFlow()

    private val _action: MutableSharedFlow<A> = MutableSharedFlow()
    val action get() = _action.asSharedFlow()

    val currentState: S get() = state.value

    init {
        subscribeEvents()
    }

    abstract fun createInitialState(): S

    protected abstract fun handleEvent(action: A)

    protected fun setState(reduce: (S) -> S) {
        _state.update { reduce(it) }
    }

    fun setAction(action: A) {
        viewModelScope.launch { _action.emit(action) }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            action.collect {
                handleEvent(it)
            }
        }
    }

    fun launchMain(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.Main) { block() }

    fun launchDefault(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.Default) { block() }

    fun launchIO(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.IO) { block() }
}
