package com.quipper.common.mvi

/**
 * The class that the MVI-Compatible View can feed to the `MviViewModel` for processing to an
 * operation (MviAction) that will later produce an `MviResult` and reduced to an `MviViewState` at the
 * end of the pipeline for rendering in the View.
 *
 * __Intent__ ⋅⋅⋅> Interpreter ⋅⋅⋅> Action ⋅⋅⋅> Processor ⋅⋅⋅> Result ⋅⋅⋅> Reducer ⋅⋅⋅> State ⋅⋅⋅> Render
 *
 * In MVI, Intent is the output a User can send to the User Interface (View); and what the View sends
 * to the ViewModel for interpretation into an Action.
 * This can be invoked automatically or implicitly when a User opens a View, or can be explicitly
 * invoked when a User directly interacts with the User Interface, ie. clicks or swipes.
 *
 * @see MviViewState
 * @see MviResult
 * @see MviAction
 * @see MviViewModel
 */
interface MviIntent
