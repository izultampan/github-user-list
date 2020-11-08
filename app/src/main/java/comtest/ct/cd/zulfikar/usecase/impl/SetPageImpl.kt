package comtest.ct.cd.zulfikar.usecase.impl

import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.usecase.SetPage
import javax.inject.Inject

class SetPageImpl @Inject constructor(
    private val userRepository: UserRepository
) : SetPage {

    override fun execute(page: Int) {
        userRepository.setPage(page)
    }
}
