package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviIntent
import comtest.ct.cd.zulfikar.user.UserListOrderBy

sealed class UserListIntent : MviIntent {
    data class SetSortSettingIntent(val sort: UserListOrderBy, val query: String) : UserListIntent()
    data class LoadUserListByNameIntent(val isPullToRefresh: Boolean, val query: String) : UserListIntent()
    data class LoadMoreUserListIntent(val page: Int, val query: String): UserListIntent()
}
