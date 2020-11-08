package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviViewEffect
import comtest.ct.cd.zulfikar.user.UserListOrderBy

sealed class UserListViewEffect : MviViewEffect {

    data class SortByChangedViewEffect(val sortBy: UserListOrderBy) : UserListViewEffect()

}
