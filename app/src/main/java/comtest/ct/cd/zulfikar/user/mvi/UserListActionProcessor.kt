package comtest.ct.cd.zulfikar.user.mvi

import comtest.ct.cd.zulfikar.usecase.FetchUserList
import comtest.ct.cd.zulfikar.usecase.SetPage
import comtest.ct.cd.zulfikar.usecase.SetSortSetting
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListActionProcessor(
    private val viewModelScope: CoroutineScope,
    private val fetchUserList: FetchUserList,
    private val setSortSetting: SetSortSetting,
    private val setPage: SetPage
) {
    fun processAction(action: UserListAction): Observable<UserListResult> {
        return when (action) {
            is UserListAction.LoadUserListByNameAction -> toResult(
                initialResult = UserListResult.LoadUserLisByNameResult.Loading,
                resultSuccessBlock = {
                    if (action.isPullToRefresh) {
                        setPage.execute(1)
                    }
                    val result = fetchUserList.execute(action.query)
                    if (result.isEmpty()) {
                        UserListResult.LoadUserLisByNameResult.Empty
                    } else {
                        UserListResult.LoadUserLisByNameResult.Success(action.isPullToRefresh, result)
                    }
                },
                resultErrorBlock = {
                    UserListResult.LoadUserLisByNameResult.Error(action.isPullToRefresh, it)
                }
            )
            is UserListAction.SetSortSettingAction -> toResult(
                initialResult = UserListResult.SetSortSettingResult.Loading,
                resultSuccessBlock = {
                    setSortSetting.execute(action.sort)
                    val result = fetchUserList.execute(action.query)
                    if (result.isEmpty()) {
                        UserListResult.SetSortSettingResult.Empty
                    } else {
                        UserListResult.SetSortSettingResult.Success(result)
                    }
                },
                resultErrorBlock = {
                    UserListResult.SetSortSettingResult.Error(it)
                }
            )
            is UserListAction.LoadMoreUserListAction -> toResult(
                resultSuccessBlock = {
                    setPage.execute(action.page)
                    val result = fetchUserList.execute(action.query)
                    if (result.isEmpty()) {
                        UserListResult.LoadMoreUserListResult.Empty
                    } else {
                        UserListResult.LoadMoreUserListResult.Success(action.page, action.page + 1, result)
                    }
                },
                resultErrorBlock = {
                    UserListResult.LoadMoreUserListResult.Error(it)
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


    fun toResult(
        resultSuccessBlock: suspend () -> UserListResult,
        resultErrorBlock: (Exception) -> UserListResult
    ): Observable<UserListResult> {
        return Observable.create { emitter ->
            viewModelScope.launch {
                try {
                    val result = withContext(Dispatchers.IO) {
                        resultSuccessBlock()
                    }
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
