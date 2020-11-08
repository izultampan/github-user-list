package comtest.ct.cd.zulfikar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import comtest.ct.cd.zulfikar.db.dao.UserDao
import comtest.ct.cd.zulfikar.db.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = true
)
abstract class GithubDB : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DB_NAME = "GITHUB_USER_DB"

        @Volatile
        private var instance: GithubDB? = null
        fun getInstance(context: Context): GithubDB {
            return instance ?: synchronized(this) {
                instance ?: create(context).also {
                    instance = it
                }
            }
        }

        fun create(context: Context): GithubDB {
            return Room.databaseBuilder(
                context,
                GithubDB::class.java, DB_NAME
            ).build()
        }

        fun createForTest(context: Context): GithubDB {
            return Room.inMemoryDatabaseBuilder(
                context,
                GithubDB::class.java
            ).allowMainThreadQueries().build()
        }
    }
}
