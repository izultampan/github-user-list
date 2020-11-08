package comtest.ct.cd.zulfikar.repository.impl

import comtest.ct.cd.zulfikar.db.dao.UserDao
import comtest.ct.cd.zulfikar.network.GithubService
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.User
import comtest.ct.cd.zulfikar.user.UserListOrderBy
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun fetchUserList(query: String, sort: UserListOrderBy, page: Long, perPage: Long): List<User> {
        return githubService.fetchUserList(
            query.toLowerCase(), sort.key, page, perPage
        ).filter {
            !it.login.startsWith(query)
        }
    }

    override suspend fun getUserListByName(name: String): List<User> {
        return userDao.getUserList().map {
            User.fromDao(it)
        }
    }
}
