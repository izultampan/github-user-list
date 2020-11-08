package comtest.ct.cd.zulfikar.usecase.impl

import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.Items
import comtest.ct.cd.zulfikar.usecase.FetchUserList
import javax.inject.Inject

class FetchUserListImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchUserList {

    override suspend fun execute(query: String): List<Items> {
        return userRepository.fetchUserList(query)
    }
}
