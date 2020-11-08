package com.quipper.common.mvi

interface MviReducer<VS: MviViewState, R: MviResult> {

    fun reduce(previous: VS, result: R): VS
}
