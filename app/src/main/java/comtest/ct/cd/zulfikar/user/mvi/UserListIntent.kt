package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviIntent
import comtest.ct.cd.zulfikar.user.UserListOrderBy

sealed class UserListIntent : MviIntent {

    data class LoadUserListByNameIntent(
        val query: String,
        val sort: UserListOrderBy,
        val page: Long
    ) : UserListIntent()
}
