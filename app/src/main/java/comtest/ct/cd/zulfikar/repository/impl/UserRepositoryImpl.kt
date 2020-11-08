package comtest.ct.cd.zulfikar.repository.impl

import comtest.ct.cd.zulfikar.network.GithubService
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.Items
import comtest.ct.cd.zulfikar.user.UserListOrderBy
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService
) : UserRepository {

    private var sort = UserListOrderBy.NAME_ASC
    private var page = 1

    override fun setSortSetting(sort: UserListOrderBy) {
        this.sort = sort
    }

    override fun setPage(page: Int) {
        this.page = page
    }

    override suspend fun fetchUserList(query: String): List<Items> {
        val value = if (query.isNotBlank()) {
            githubService.fetchUserList(
                query.toLowerCase(),
                sort.key,
                page
            ).items.filter {
                it.login.startsWith(query)
            }
        } else {
            emptyList()
        }
        return when (sort) {
            UserListOrderBy.NAME_ASC -> {
                value.sortedBy {
                    it.login
                }
            }
            UserListOrderBy.NAME_DESC -> {
                value.sortedByDescending {
                    it.login
                }
            }
        }
    }
}
