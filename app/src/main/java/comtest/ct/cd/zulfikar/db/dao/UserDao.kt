package comtest.ct.cd.zulfikar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import comtest.ct.cd.zulfikar.db.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE login LIKE '%' || :title")
    fun getUserByTitle(vararg title: String): List<UserEntity>

    @Query("SELECT * FROM user")
    fun getUserList(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceList(data: List<UserEntity>)
}
