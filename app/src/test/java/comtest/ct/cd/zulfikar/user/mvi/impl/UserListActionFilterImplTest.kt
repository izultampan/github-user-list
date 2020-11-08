package comtest.ct.cd.zulfikar.user.mvi.impl

import comtest.ct.cd.zulfikar.user.mvi.UserListAction
import comtest.ct.cd.zulfikar.user.mvi.UserListActionFilter
import comtest.ct.cd.zulfikar.user.mvi.UserListIntent
import io.reactivex.Observable
import net.bytebuddy.utility.RandomString
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

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
        val observable = Observable.just(
            UserListIntent.LoadUserListByNameIntent(dummyName)
        )
        val result = observable.compose(filter).test()
        assertTrue(result.valueCount() == 1)
        result.assertValueAt(0) {
            it is UserListIntent.LoadUserListByNameIntent &&
                    it.name == dummyName
        }
    }


    @Test
    fun `actionFromIntent should convert from LoadUserListByNameIntent to LoadUserListByNameAction`() {
        val dummyName = RandomString.make()
        val dummyIntent = UserListIntent.LoadUserListByNameIntent(dummyName)
        val action = userListActionFilter.actionFromIntent(dummyIntent)
        assertTrue(
            action is UserListAction.LoadUserListByNameAction &&
                    action.name == dummyIntent.name
        )
    }
}
