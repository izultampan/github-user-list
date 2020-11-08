package comtest.ct.cd.zulfikar.usecase

import comtest.ct.cd.zulfikar.schema.User

interface GetUserList {

    suspend fun execute(): List<User>
}
