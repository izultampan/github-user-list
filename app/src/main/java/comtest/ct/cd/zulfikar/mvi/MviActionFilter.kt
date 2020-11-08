package com.quipper.common.mvi

import io.reactivex.ObservableTransformer

interface MviActionFilter<I : MviIntent, A : MviAction> {
    fun filter(): ObservableTransformer<I, I>
    fun actionFromIntent(intent: I): A
}
