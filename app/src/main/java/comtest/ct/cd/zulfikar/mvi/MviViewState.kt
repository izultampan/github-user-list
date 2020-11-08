package com.quipper.common.mvi

/**
 * The Single Source of Truth of the View's render method.
 *
 * The MviViewState provides the payload and flags that will be used by the components and attributes of
 * the View in the render step.
 *
 * Intent ⋅⋅⋅> Interpreter ⋅⋅⋅> Action ⋅⋅⋅> Processor ⋅⋅⋅> Result ⋅⋅⋅> Reducer ⋅⋅⋅> __State__ ⋅⋅⋅> Render
 */
interface MviViewState
