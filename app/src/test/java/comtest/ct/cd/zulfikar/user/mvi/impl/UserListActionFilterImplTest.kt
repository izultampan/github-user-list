package comtest.ct.cd.zulfikar.user.mvi.impl

import comtest.ct.cd.zulfikar.user.UserListOrderBy
import comtest.ct.cd.zulfikar.user.mvi.UserListAction
import comtest.ct.cd.zulfikar.user.mvi.UserListActionFilter
import comtest.ct.cd.zulfikar.user.mvi.UserListIntent
import io.reactivex.Observable
import net.bytebuddy.utility.RandomString
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class UserListActionFilterImplTest {

    private lateinit var userListActionFilter: UserListActionFilter

    @Before
    fun setUp() {
        userListActionFilter = UserListActionFilterImpl()
    }

    @Test
    fun `LoadUserListByNameIntent should pass the filter`() {
        val filter = userListActionFilter.filter()
        val dummyName = RandomString.make()
        val dummyPage = Random.nextLong()
        val dummyOrder = UserListOrderBy.NAME_ASC
        val dummyIntent =             UserListIntent.LoadUserListByNameIntent(dummyName, dummyOrder, dummyPage)

        val observable = Observable.just(
            dummyIntent
        )
        val result = observable.compose(filter).test()
        assertTrue(result.valueCount() == 1)
        result.assertValueAt(0) {
            it is UserListIntent.LoadUserListByNameIntent &&
                    it.query == dummyName &&
                    it.page == dummyPage &&
                    it.sort == dummyOrder
        }
    }


    @Test
    fun `actionFromIntent should convert from LoadUserListByNameIntent to LoadUserListByNameAction`() {
        val dummyName = RandomString.make()
        val dummyPage = Random.nextLong()
        val dummyOrder = UserListOrderBy.NAME_ASC
        val dummyIntent =             UserListIntent.LoadUserListByNameIntent(dummyName, dummyOrder, dummyPage)
        val action = userListActionFilter.actionFromIntent(dummyIntent)
        assertTrue(
            action is UserListAction.LoadUserListByNameAction &&
                    action.query == dummyIntent.query &&
                    action.page == dummyIntent.page
        )
    }
}
