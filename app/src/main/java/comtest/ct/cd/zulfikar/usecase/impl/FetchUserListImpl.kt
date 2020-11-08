package comtest.ct.cd.zulfikar.usecase.impl

import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.User
import comtest.ct.cd.zulfikar.usecase.FetchUserList
import comtest.ct.cd.zulfikar.user.UserListOrderBy
import javax.inject.Inject

class FetchUserListImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchUserList {

    override suspend fun execute(query: String, sort: UserListOrderBy, page: Long): List<User> {
        return userRepository.fetchUserList(
            query, sort, page
        )
    }
}
