package comtest.ct.cd.zulfikar.user.mvi

import comtest.ct.cd.zulfikar.usecase.FetchUserList
import comtest.ct.cd.zulfikar.usecase.GetUserList
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListActionProcessor(
    private val viewModelScope: CoroutineScope,
    private val fetchUserList: FetchUserList,
    private val getUserList: GetUserList
) {
    fun processAction(action: UserListAction): Observable<UserListResult> {
        return when (action) {
            is UserListAction.LoadUserListByNameAction -> toResult(
                initialResult = UserListResult.LoadUserLisByNameResult.Loading,
                resultSuccessBlock = {
                    fetchUserList.execute()
                    val result = getUserList.execute(action.name)
                    UserListResult.LoadUserLisByNameResult.Success(result)
                },
                resultErrorBlock = {
                    val result = getUserList.execute(action.name)
                    UserListResult.LoadUserLisByNameResult.Error(result, it)
                }
            )
        }
    }

    private fun toResult(
        resultSuccessBlock: suspend CoroutineScope.() -> UserListResult,
        resultErrorBlock: suspend (Exception) -> UserListResult,
        initialResult: UserListResult
    ): Observable<UserListResult> {
        return Observable.create { emitter ->
            viewModelScope.launch {
                emitter.onNext(initialResult)
                try {
                    val result = withContext(Dispatchers.IO) { resultSuccessBlock() }
                    emitter.onNext(result)
                    emitter.onComplete()
                } catch (error: Exception) {
                    val result = resultErrorBlock(error)
                    emitter.onNext(result)
                    emitter.onComplete()
                }
            }
        }
    }

}
