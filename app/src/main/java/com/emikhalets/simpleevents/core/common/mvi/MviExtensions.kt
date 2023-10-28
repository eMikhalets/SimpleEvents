package com.emikhalets.simpleevents.core.common.mvi

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun <A : MviAction, E : MviEffect, S : MviState> MviViewModel<A, E, S>.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return viewModelScope.launch { block() }
}

fun <A : MviAction, E : MviEffect, S : MviState> MviViewModel<A, E, S>.launchDefault(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return viewModelScope.launch(Dispatchers.Default) { block() }
}

fun <A : MviAction, E : MviEffect, S : MviState> MviViewModel<A, E, S>.launchMain(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return viewModelScope.launch(Dispatchers.Main) { block() }
}

fun <A : MviAction, E : MviEffect, S : MviState> MviViewModel<A, E, S>.launchUnconfined(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return viewModelScope.launch(Dispatchers.Unconfined) { block() }
}

fun <A : MviAction, E : MviEffect, S : MviState> MviViewModel<A, E, S>.launchIO(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return viewModelScope.launch(Dispatchers.IO) { block() }
}
