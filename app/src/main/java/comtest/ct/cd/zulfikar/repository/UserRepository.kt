package comtest.ct.cd.zulfikar.repository

import comtest.ct.cd.zulfikar.schema.Items
import comtest.ct.cd.zulfikar.user.UserListOrderBy

interface UserRepository {
    fun setSortSetting(sort: UserListOrderBy)
    fun setPage(page: Int)
    suspend fun fetchUserList(query: String): List<Items>
}
