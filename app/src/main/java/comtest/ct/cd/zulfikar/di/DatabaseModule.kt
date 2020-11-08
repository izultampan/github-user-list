package comtest.ct.cd.zulfikar.di

import android.content.Context
import comtest.ct.cd.zulfikar.db.GithubDB
import comtest.ct.cd.zulfikar.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDB {
        return GithubDB.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(githubDB: GithubDB): UserDao {
        return githubDB.userDao()
    }
}
