package comtest.ct.cd.zulfikar.user.mvi.impl

import comtest.ct.cd.zulfikar.user.mvi.UserListReducer
import comtest.ct.cd.zulfikar.user.mvi.UserListResult
import comtest.ct.cd.zulfikar.user.mvi.UserListViewState
import javax.inject.Inject

class UserListReducerImpl @Inject constructor() : UserListReducer {
    override fun reduce(previous: UserListViewState, result: UserListResult): UserListViewState {
        return when (result) {
            is UserListResult.SetSortSettingResult -> when (result) {
                is UserListResult.SetSortSettingResult.Loading ->
                    previous.copy(isLoading = true)
                is UserListResult.SetSortSettingResult.Error ->
                    previous.copy(isLoading = false, error = result.error)
                is UserListResult.SetSortSettingResult.Success ->
                    previous.copy(isLoading = false, userList = result.list)
                is UserListResult.SetSortSettingResult.Empty ->
                    previous.copy(
                        isLoading = false,
                        error = null
                    )
            }
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
                is UserListResult.LoadUserLisByNameResult.Empty ->
                    previous.copy(isLoading = false, error = null)
            }
            is UserListResult.LoadMoreUserListResult -> when (result) {
                is UserListResult.LoadMoreUserListResult.Success -> {
                    val userList = previous.userList.toMutableList()
                    userList.addAll(result.list)

                    previous.copy(
                        currentPage = result.currentPage,
                        nextPage = result.nextPage,
                        userList = userList
                    )
                }
                is UserListResult.LoadMoreUserListResult.Empty ->
                    previous
                is UserListResult.LoadMoreUserListResult.Error ->
                    previous.copy(
                        error = result.error
                    )
            }
        }
    }
}
