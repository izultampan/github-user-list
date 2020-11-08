package comtest.ct.cd.zulfikar.schema

import comtest.ct.cd.zulfikar.db.entity.UserEntity

data class User(

    val login: String,
    val id: Int,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String,
    val url: String,
    val htmlUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val organizationsUrl: String,
    val reposUrl: String,
    val eventsUrl: String,
    val receivedEventsUrl: String,
    val type: String,
    val siteAdmin: Boolean
) {
    companion object {
        fun fromDao(entity: UserEntity): User {
            return User(
                entity.login,
                entity.id,
                entity.nodeId,
                entity.avatarUrl,
                entity.gravatarId,
                entity.url,
                entity.htmlUrl,
                entity.followersUrl,
                entity.followingUrl,
                entity.gistsUrl,
                entity.starredUrl,
                entity.subscriptionsUrl,
                entity.organizationsUrl,
                entity.reposUrl,
                entity.eventsUrl,
                entity.receivedEventsUrl,
                entity.type,
                entity.siteAdmin
            )
        }
    }

    fun toDao(): UserEntity {
        return UserEntity(
            login,
            id,
            nodeId,
            avatarUrl,
            gravatarId,
            url,
            htmlUrl,
            followersUrl,
            followingUrl,
            gistsUrl,
            starredUrl,
            subscriptionsUrl,
            organizationsUrl,
            reposUrl,
            eventsUrl,
            receivedEventsUrl,
            type,
            siteAdmin
        )
    }
}
