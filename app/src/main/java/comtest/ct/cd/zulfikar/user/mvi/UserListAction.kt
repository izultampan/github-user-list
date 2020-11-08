package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviAction

sealed class UserListAction: MviAction {

    data class LoadUserListByNameAction(val name: String): UserListAction()
}
