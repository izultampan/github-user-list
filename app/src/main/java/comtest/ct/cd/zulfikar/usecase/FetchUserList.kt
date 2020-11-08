package comtest.ct.cd.zulfikar.usecase

import comtest.ct.cd.zulfikar.schema.User
import comtest.ct.cd.zulfikar.user.UserListOrderBy

interface FetchUserList {

    suspend fun execute(query: String, sort: UserListOrderBy, page: Long): List<User>
}
