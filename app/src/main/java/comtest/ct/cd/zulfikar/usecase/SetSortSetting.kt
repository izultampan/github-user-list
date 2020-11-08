package comtest.ct.cd.zulfikar.usecase

import comtest.ct.cd.zulfikar.user.UserListOrderBy

interface SetSortSetting {
    fun execute(sort: UserListOrderBy)
}
