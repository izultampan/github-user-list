package comtest.ct.cd.zulfikar.user.mvi.impl

import comtest.ct.cd.zulfikar.user.mvi.UserListReducer
import comtest.ct.cd.zulfikar.user.mvi.UserListResult
import comtest.ct.cd.zulfikar.user.mvi.UserListViewState
import javax.inject.Inject

class UserListReducerImpl @Inject constructor(): UserListReducer {
    override fun reduce(previous: UserListViewState, result: UserListResult): UserListViewState {
        return previous
    }
}
