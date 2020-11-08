package comtest.ct.cd.zulfikar.usecase.impl

import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.usecase.SetSortSetting
import comtest.ct.cd.zulfikar.user.UserListOrderBy
import javax.inject.Inject

class SetSortSettingImpl @Inject constructor(
    private val userRepository: UserRepository
) : SetSortSetting {
    override fun execute(sort: UserListOrderBy) {
        userRepository.setSortSetting(sort)
    }
}
