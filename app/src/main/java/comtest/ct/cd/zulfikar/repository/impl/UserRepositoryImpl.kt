package comtest.ct.cd.zulfikar.repository.impl

import comtest.ct.cd.zulfikar.data.network.GithubService
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService
) : UserRepository {
    override suspend fun fetchUserList(): List<User> {
        return githubService.fetchUserList()
    }
}
