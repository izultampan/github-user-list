package comtest.ct.cd.zulfikar.di

import comtest.ct.cd.zulfikar.user.mvi.UserListActionFilter
import comtest.ct.cd.zulfikar.user.mvi.UserListReducer
import comtest.ct.cd.zulfikar.user.mvi.UserListViewEffectSender
import comtest.ct.cd.zulfikar.user.mvi.impl.UserListActionFilterImpl
import comtest.ct.cd.zulfikar.user.mvi.impl.UserListReducerImpl
import comtest.ct.cd.zulfikar.user.mvi.impl.UserListViewEffectSenderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class MviModule {

    @Binds
    abstract fun bindsUserListActionFilter(userListActionFilterImpl: UserListActionFilterImpl): UserListActionFilter

    @Binds
    abstract fun bindUserListReducer(userListReducerImpl: UserListReducerImpl): UserListReducer

    @Binds
    abstract fun bindUserListSender(userListViewEffectSenderImpl: UserListViewEffectSenderImpl): UserListViewEffectSender
}
