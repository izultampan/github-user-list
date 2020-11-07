package comtest.ct.cd.zulfikar.repository

import comtest.ct.cd.zulfikar.schema.User

interface UserRepository {

    suspend fun fetchUserList(): List<User>
}
