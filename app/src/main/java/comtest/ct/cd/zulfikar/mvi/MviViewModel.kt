package com.quipper.common.mvi

import io.reactivex.Observable

/**
 * Provides the minimum methods a ViewModel must implement to be MVI-Compatible.
 *
 * **Guide:**
 *
 * Besides the methods in this interface, the `ViewModel` is encouraged to implement the following methods:
 *
 * 1. A __BiFunction__ that reduces a _Previous State_ into a _New State_ depending on an emitted _Result_
 * from an _Action_.
 *      - Ex. `reduce(prevState : <S>, result : <R>) : <S>`
 *
 * 2. A __mapper function__ that interprets an `MviIntent` into an `MviAction`
 *      - The _mapper_ function that will be supplied in the map method of the `Observable<T>`
 *
 * 3. A __composer function__ that returns an Observable<S>.
 *      - Ex. `compose() : Observable<S>`
 *      - This will be the composer of the Observable variable in `MviViewModel.getStates`
 *      - Typically implemented like such:
 *          ```
 *          return subject
 *              .compose(ObservableTransformerFilter)       //Filters only the allowed MviIntents
 *              .map(this::mapperFunction)                  //Transforms an MviIntent into an MviAction
 *              .compose(actionProcessor.actionProcessor)   //Processes an MviAction to an MviResult
 *              .scan(
 *                  MviViewStateClass.myInitialState(),
 *                  this::reduceBiFunction                  // Emits an MviState per MviResult
 *              )
 *              .distinctUntilChanged()
 *              .replay(1)
 *              .autoConnect(0)
 *          ```
 *
 * __Where:__ `<I>` implements `MviIntent`, `<S>` implements `MviViewState`, and `<R>` implements `MviResult`
 *
 * The ViewModel also need to have an Action Processor, which is just an ObservableTransformer that
 * converts MviActions to MviResults.
 *
 * **The basic interaction pipeline:**
 *
 * Intent ⋅⋅⋅> Interpreter ⋅⋅⋅> Action ⋅⋅⋅> Processor ⋅⋅⋅> Result ⋅⋅⋅> Reducer ⋅⋅⋅> State ⋅⋅⋅> Render
 *
 * @see MviIntent
 * @see MviViewState
 * @see MviResult
 * @see MviAction
 * @see androidx.lifecycle.ViewModel.ViewModel
 */
interface MviViewModel<I : MviIntent, S : MviViewState> {

    /**
     * Accepts Observable<I> from an MVI-Compatible View (activity/fragments) and subscribe
     * from it.
     *
     * When subscribing to intents, use `PublishSubject<I>` as argument for
     * `intent.subscribe(observer: <T>)`
     *
     * To know <I>
     * @see MviViewModel
     *
     * @param intents
     *          The supplied Observable intents with type <I>
     */
    fun processIntents(intents: Observable<I>)

    /**
     * The `Observable` that emits `MviViewState` for use of the MVI-Compatible View when rendering
     *
     * The MVI-Compatible View uses the MviViewState as the single source of truth for rendering
     * state-reliant components.
     *
     * By subscribing to `MviViewModel.getStates`, the render function is called to change a component's
     * attribute depending on the new state emitted.
     *
     * @see MviViewState
     */
    fun getStates(): Observable<S>
}
