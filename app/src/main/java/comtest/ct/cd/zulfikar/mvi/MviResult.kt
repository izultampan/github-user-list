package com.quipper.common.mvi

/**
 * The Result produced by the Action Processor in the ViewModel Layer.
 *
 * This is the class that is fed to the reducer function in the ViewModel which produces the ViewStates
 *
 * Intent ⋅⋅⋅> Interpreter ⋅⋅⋅> Action ⋅⋅⋅> Processor ⋅⋅⋅> __Result__ ⋅⋅⋅> Reducer ⋅⋅⋅> State ⋅⋅⋅> Render
 *
 * The reducer function is a method that creates and returns a new Immutable State based from the
 * Previous State and Result from the Action Processor.
 *
 * @see MviAction
 * @see MviViewState
 * @see MviViewModel
 */
interface MviResult
