package comtest.ct.cd.zulfikar.user.mvi.impl

import comtest.ct.cd.zulfikar.user.mvi.UserListAction
import comtest.ct.cd.zulfikar.user.mvi.UserListActionFilter
import comtest.ct.cd.zulfikar.user.mvi.UserListIntent
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserListActionFilterImpl @Inject constructor() : UserListActionFilter {
    override fun filter(): ObservableTransformer<UserListIntent, UserListIntent> {
        return ObservableTransformer { intentObservable ->
            intentObservable.publish { shared ->
                Observable.merge(
                    listOf(
                        shared.ofType(UserListIntent.LoadUserListByNameIntent::class.java)
                            .debounce(500, TimeUnit.MILLISECONDS)
                    )
                )
                    .cast(UserListIntent::class.java)
                    .mergeWith(
                        shared.filter {
                            it is UserListIntent.SetSortSettingIntent
                        }
                    )
            }
        }
    }

    override fun actionFromIntent(intent: UserListIntent): UserListAction {
        return when (intent) {
            is UserListIntent.LoadUserListByNameIntent -> UserListAction.LoadUserListByNameAction(
                intent.isPullToRefresh, intent.query
            )
            is UserListIntent.SetSortSettingIntent -> UserListAction.SetSortSettingAction(
                intent.sort
            )
        }
    }
}
