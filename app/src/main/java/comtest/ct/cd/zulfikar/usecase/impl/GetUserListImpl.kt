package comtest.ct.cd.zulfikar.usecase.impl

import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.User
import comtest.ct.cd.zulfikar.usecase.GetUserList
import javax.inject.Inject

class GetUserListImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserList {
    override suspend fun execute(): List<User> {
        return userRepository.getUserList()
    }
}
