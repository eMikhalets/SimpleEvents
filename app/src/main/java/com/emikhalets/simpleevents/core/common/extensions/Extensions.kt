package com.emikhalets.simpleevents.core.common.extensions

import com.emikhalets.simpleevents.domain.AppResult
import com.emikhalets.simpleevents.domain.StringValue

suspend fun <T> execute(block: suspend () -> T): AppResult<T> {
    return try {
        AppResult.Success(block())
    } catch (e: Exception) {
        loge(e)
        AppResult.Failure(StringValue.create())
    }
}
