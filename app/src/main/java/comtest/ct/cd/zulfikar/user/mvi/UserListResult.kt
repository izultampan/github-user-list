package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviResult
import comtest.ct.cd.zulfikar.schema.User

sealed class UserListResult : MviResult {

    sealed class LoadUserLisByNameResult : UserListResult() {
        object Loading : LoadUserLisByNameResult()
        data class Success(val list: List<User>) : LoadUserLisByNameResult()
        data class Error(val error: Exception) : LoadUserLisByNameResult()
    }
}
