package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviIntent

sealed class UserListIntent : MviIntent {

    data class LoadUserListByNameIntent(val name: String):  UserListIntent()
}
