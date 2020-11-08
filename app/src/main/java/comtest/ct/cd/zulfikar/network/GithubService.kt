package comtest.ct.cd.zulfikar.network

import comtest.ct.cd.zulfikar.schema.User
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    suspend fun fetchUserList(
        @Query("q") query: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): User
}
