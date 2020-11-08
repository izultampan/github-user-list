package comtest.ct.cd.zulfikar

import net.bytebuddy.utility.RandomString
import kotlin.random.Random

object Dummy {

    fun createUser(): User {
        return User(
            login = RandomString.make(),
            id = Random.nextInt(),
            nodeId = RandomString.make(),
            avatarUrl = RandomString.make(),
            gravatarId = RandomString.make(),
            url = RandomString.make(),
            htmlUrl = RandomString.make(),
            followersUrl = RandomString.make(),
            followingUrl = RandomString.make(),
            gistsUrl = RandomString.make(),
            starredUrl = RandomString.make(),
            subscriptionsUrl = RandomString.make(),
            organizationsUrl = RandomString.make(),
            reposUrl = RandomString.make(),
            eventsUrl = RandomString.make(),
            receivedEventsUrl = RandomString.make(),
            type = RandomString.make(),
            siteAdmin = true
        )
    }
}
