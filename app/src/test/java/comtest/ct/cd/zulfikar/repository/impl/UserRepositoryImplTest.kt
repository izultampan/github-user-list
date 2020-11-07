package comtest.ct.cd.zulfikar.repository.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import comtest.ct.cd.zulfikar.CoroutineTestRule
import comtest.ct.cd.zulfikar.data.network.GithubService
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import net.bytebuddy.utility.RandomString
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(
        TestCoroutineDispatcher()
    )

    @Mock
    private lateinit var mockedGithubService: GithubService

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepositoryImpl(
            githubService = mockedGithubService
        )
    }

    @Test
    fun `fetchUserList should return list of users when get success response from webservice`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val dummyUser: User = mock()
            val expectedList = listOf(dummyUser)
            whenever(mockedGithubService.fetchUserList()).thenReturn(expectedList)
            val userList = userRepository.fetchUserList()
            assertEquals(expectedList, userList)
        }
    }

    @Test
    fun `fetchUserList should return empty result when get success response from webservice but empty result`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val expectedList = emptyList<User>()
            whenever(mockedGithubService.fetchUserList()).thenReturn(expectedList)
            val userList = userRepository.fetchUserList()
            assertEquals(expectedList, userList)
        }
    }

    @Test
    fun `fetchUserList should return exception when error response from webservice`() {
        coroutineTestRule.runBlockingErrorTest(
            testFunction = {
                val expectedException = MockitoException(RandomString.make(2))
                whenever(mockedGithubService.fetchUserList()).thenThrow(expectedException)
                userRepository.fetchUserList()
            },
            verifyFunction = {
                it is MockitoException
            }
        )
    }
}
