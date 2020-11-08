package comtest.ct.cd.zulfikar.usecase.impl

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import comtest.ct.cd.zulfikar.CoroutineTestRule
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.usecase.FetchUserList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import net.bytebuddy.utility.RandomString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException

class FetchUserListImplTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(
        TestCoroutineDispatcher()
    )

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var fetchUserList: FetchUserList

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fetchUserList = FetchUserListImpl(
            userRepository = userRepository
        )
    }

    @Test
    fun `execute should calling fetchUser function from userRepository`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            fetchUserList.execute()
            verify(fetchUserList).execute()
        }
    }

    @Test
    fun `execute should return error when get invalid value from service`() {
        coroutineTestRule.runBlockingErrorTest(
            testFunction = {
                whenever(userRepository.fetchUserList()).thenThrow(MockitoException(RandomString.make()))
                fetchUserList.execute()
            },
            verifyFunction = {
                it is MockitoException
            }
        )
    }
}
