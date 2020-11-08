package com.quipper.common.mvi

/**
 * The namespace for operations to be interpreted by the `MviViewModel`'s Action Processor
 *
 * Each `MviIntent` can be mapped to an MviAction, then this MviAction will be processed into a real
 * operation by the Action Processor which will produce an MviResult.
 *
 * Intent ⋅⋅⋅> Interpreter ⋅⋅⋅> __Action__ ⋅⋅⋅> Processor ⋅⋅⋅> Result ⋅⋅⋅> Reducer ⋅⋅⋅> State ⋅⋅⋅> Render
 *
 * Classes implementing `MviAction` should not have any operations at all, it can be thought of as a
 * glorified Enum with flags, action parameters, and payload.
 *
 * Action Processors can simply be a class that contains ObservableTransformers that automatically
 * executes an operation in the Repository. The `MviViewModel` should supply the ObservableTransformer
 * to the composer function for the State Observable. (See MviViewModel class doc item 3)
 *
 * @see MviResult
 * @see MviViewModel
 */
interface MviAction
