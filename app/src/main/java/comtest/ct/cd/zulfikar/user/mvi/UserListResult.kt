package comtest.ct.cd.zulfikar.user.mvi

import com.quipper.common.mvi.MviResult
import comtest.ct.cd.zulfikar.schema.Items
import comtest.ct.cd.zulfikar.user.UserListOrderBy

sealed class UserListResult : MviResult {
    data class SetQueryResult(val query: String) : UserListResult()
    sealed class SetSortSettingResult : UserListResult() {
        object Loading : SetSortSettingResult()
        data class Success(val list: List<Items>) :
            SetSortSettingResult()

        data class Error(val error: Exception) : SetSortSettingResult()
    }
    sealed class LoadUserLisByNameResult : UserListResult() {
        object Loading : LoadUserLisByNameResult()
        data class Success(val isPullToRefresh: Boolean, val list: List<Items>) :
            LoadUserLisByNameResult()

        data class Error(val isPullToRefresh: Boolean, val error: Exception) : LoadUserLisByNameResult()
    }
}
