package com.quipper.common.mvi

import io.reactivex.Observable

interface MviViewEffectSender<R, VE : MviViewEffect> {
    fun handleViewEffect(result: R)
    fun getViewEffect(): Observable<VE>
}
