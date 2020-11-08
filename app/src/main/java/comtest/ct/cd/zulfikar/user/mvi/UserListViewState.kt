package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviViewState
import comtest.ct.cd.zulfikar.schema.User

sealed class UserListViewState : MviViewState {
    object InitialState : UserListViewState()
    data class ShowUserList(
        val userList: List<User>
    ) : UserListViewState()

    data class ShowError(
        val error: Exception
    ) : UserListViewState()

    object ShowLoading : UserListViewState()
}
