package comtest.ct.cd.zulfikar.usecase

import comtest.ct.cd.zulfikar.schema.Items

interface GetUserList {

    suspend fun execute(name: String): List<Items>
}
