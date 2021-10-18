package com.kerubyte.engagecommerce.infrastructure.util

import com.kerubyte.engagecommerce.data.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl: DispatcherProvider {

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}