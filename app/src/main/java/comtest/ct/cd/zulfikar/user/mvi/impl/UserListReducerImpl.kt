package comtest.ct.cd.zulfikar.user.mvi.impl

import comtest.ct.cd.zulfikar.user.mvi.UserListReducer
import comtest.ct.cd.zulfikar.user.mvi.UserListResult
import comtest.ct.cd.zulfikar.user.mvi.UserListViewState
import javax.inject.Inject

class UserListReducerImpl @Inject constructor() : UserListReducer {
    override fun reduce(previous: UserListViewState, result: UserListResult): UserListViewState {
        return when (result) {
            is UserListResult.SetSortSettingResult ->
                previous.copy(
                    sort = result.sort
                )
            is UserListResult.SetQueryResult ->
                previous.copy(
                    query = result.query
                )
            is UserListResult.LoadUserLisByNameResult -> when (result) {
                is UserListResult.LoadUserLisByNameResult.Loading ->
                    previous.copy(
                        isLoading = true,
                        error = null
                    )
                is UserListResult.LoadUserLisByNameResult.Error ->
                    previous.copy(
                        isLoading = false,
                        error = result.error
                    )
                is UserListResult.LoadUserLisByNameResult.Success ->
                    previous.copy(
                        isLoading = false,
                        error = null,
                        userList = result.list
                    )
            }
        }
    }
}
