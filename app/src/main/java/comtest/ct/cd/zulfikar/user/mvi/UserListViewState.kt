package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviViewState
import comtest.ct.cd.zulfikar.schema.Items
import comtest.ct.cd.zulfikar.user.UserListOrderBy

data class UserListViewState(
    val isLoading: Boolean,
    val isPullToRefresh: Boolean,
    val page: Long,
    val sort: UserListOrderBy,
    val userList: List<Items>,
    val query: String?,
    val error: Exception?
) : MviViewState {
    companion object {
        fun initialState(): UserListViewState {
            return UserListViewState(
                isLoading = false,
                isPullToRefresh = false,
                page = 1,
                sort = UserListOrderBy.NAME_ASC,
                userList = emptyList(),
                error = null,
                query = null
            )
        }
    }
}
