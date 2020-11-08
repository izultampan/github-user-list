package comtest.ct.cd.zulfikar.di

import comtest.ct.cd.zulfikar.usecase.FetchUserList
import comtest.ct.cd.zulfikar.usecase.GetUserList
import comtest.ct.cd.zulfikar.usecase.SetPage
import comtest.ct.cd.zulfikar.usecase.SetSortSetting
import comtest.ct.cd.zulfikar.usecase.impl.FetchUserListImpl
import comtest.ct.cd.zulfikar.usecase.impl.GetUserListImpl
import comtest.ct.cd.zulfikar.usecase.impl.SetPageImpl
import comtest.ct.cd.zulfikar.usecase.impl.SetSortSettingImpl
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

    @Binds
    abstract fun bindsSetSortSetting(setSortSetting: SetSortSettingImpl): SetSortSetting

    @Binds
    abstract fun bindsSetQuery(setQueryImpl: SetPageImpl): SetPage
}
