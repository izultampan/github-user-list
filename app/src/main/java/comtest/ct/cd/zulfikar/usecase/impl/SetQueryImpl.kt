package comtest.ct.cd.zulfikar.usecase.impl

import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.usecase.SetQuery
import javax.inject.Inject

class SetQueryImpl @Inject constructor(
    private val userRepository: UserRepository
) : SetQuery {
    override fun execute(query: String) {
//        userRepository.setQuery(query)
    }
}
