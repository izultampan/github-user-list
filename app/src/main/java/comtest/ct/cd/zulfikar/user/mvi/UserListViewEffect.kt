package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviViewEffect

sealed class UserListViewEffect : MviViewEffect {

    object ShowResultErrorViewEffect : UserListViewEffect()
    object ShowResultEmptyViewEffect : UserListViewEffect()
}
