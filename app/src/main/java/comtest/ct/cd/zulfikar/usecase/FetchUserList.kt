package comtest.ct.cd.zulfikar.usecase

import comtest.ct.cd.zulfikar.schema.Items

interface FetchUserList {

    suspend fun execute(query: String): List<Items>
}
