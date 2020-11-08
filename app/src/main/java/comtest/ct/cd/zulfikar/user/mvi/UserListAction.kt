package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviAction
import comtest.ct.cd.zulfikar.user.UserListOrderBy

sealed class UserListAction : MviAction {
    data class SetSortSettingAction(val sort: UserListOrderBy) : UserListAction()
    data class LoadUserListByNameAction(val isPullToRefresh: Boolean, val query: String): UserListAction()
}
