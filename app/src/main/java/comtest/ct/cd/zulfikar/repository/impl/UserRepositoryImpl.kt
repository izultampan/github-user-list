package comtest.ct.cd.zulfikar.repository.impl

import comtest.ct.cd.zulfikar.db.dao.UserDao
import comtest.ct.cd.zulfikar.network.GithubService
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun fetchUserList() {
        githubService.fetchUserList()
            .map {
                it.toDao()
            }.let {
                userDao.insertOrReplaceList(it)
            }
    }

    override suspend fun getUserList(): List<User> {
        return userDao.getUserList().map {
            User.fromDao(it)
        }
    }
}
