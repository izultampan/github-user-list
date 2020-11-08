package comtest.ct.cd.zulfikar.repository

import comtest.ct.cd.zulfikar.constant.WebServiceConfigConstant
import comtest.ct.cd.zulfikar.schema.User
import comtest.ct.cd.zulfikar.user.UserListOrderBy

interface UserRepository {

    suspend fun fetchUserList(query: String, sort: UserListOrderBy, page: Long, perPage: Long = WebServiceConfigConstant.PER_PAGE): List<User>

    suspend fun getUserListByName(name: String): List<User>
}
