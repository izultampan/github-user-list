package comtest.ct.cd.zulfikar.di

import comtest.ct.cd.zulfikar.usecase.FetchUserList
import comtest.ct.cd.zulfikar.usecase.GetUserList
import comtest.ct.cd.zulfikar.usecase.impl.FetchUserListImpl
import comtest.ct.cd.zulfikar.usecase.impl.GetUserListImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class UsecaseModule {

    @Binds
    abstract fun bindsFetchUserList(fetchUserListImpl: FetchUserListImpl): FetchUserList

    @Binds
    abstract fun bindsGetUserList(getUserListImpl: GetUserListImpl): GetUserList
}
