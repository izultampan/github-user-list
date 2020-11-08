package comtest.ct.cd.zulfikar.user.mvi.impl

import comtest.ct.cd.zulfikar.user.mvi.UserListResult
import comtest.ct.cd.zulfikar.user.mvi.UserListViewEffect
import comtest.ct.cd.zulfikar.user.mvi.UserListViewEffectSender
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class UserListViewEffectSenderImpl @Inject constructor() : UserListViewEffectSender {

    private val sender = PublishSubject.create<UserListViewEffect>()

    override fun handleViewEffect(result: UserListResult) {
        when (result) {

        }
    }

    override fun getViewEffect(): Observable<UserListViewEffect> {
        return sender.serialize()
    }
}
