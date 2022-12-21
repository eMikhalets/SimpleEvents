package com.emikhalets.simpleevents.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface AppState

abstract class BaseViewModel<S : AppState> : ViewModel() {

    private val initialState: S by lazy { createInitialState() }

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state get() = _state.asStateFlow()

    val currentState: S get() = state.value

    abstract fun createInitialState(): S

    protected fun setState(reduce: (S) -> S) {
        _state.update { reduce(it) }
    }

    fun launchMain(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.Main) { block() }

    fun launchDefault(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.Default) { block() }

    fun launchIO(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.IO) { block() }
}
