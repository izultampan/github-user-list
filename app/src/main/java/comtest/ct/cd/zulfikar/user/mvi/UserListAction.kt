package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviAction
import comtest.ct.cd.zulfikar.user.UserListOrderBy

sealed class UserListAction : MviAction {

    data class LoadUserListByNameAction(
        val query: String,
        val sort: UserListOrderBy,
        val page: Long
    ) : UserListAction()
}
