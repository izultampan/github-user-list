package comtest.ct.cd.zulfikar.repository.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import comtest.ct.cd.zulfikar.CoroutineTestRule
import comtest.ct.cd.zulfikar.Dummy
import comtest.ct.cd.zulfikar.db.dao.UserDao
import comtest.ct.cd.zulfikar.db.entity.UserEntity
import comtest.ct.cd.zulfikar.network.GithubService
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

    @Mock
    private lateinit var mockedUserDao: UserDao

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepositoryImpl(
            githubService = mockedGithubService,
            userDao = mockedUserDao
        )
    }

    @Test
    fun `fetchUserList should execute fetch user from retrofit service and put result into database`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val dummyUser: User = mock()
            val expectedList = listOf(dummyUser)
            whenever(mockedGithubService.fetchUserList()).thenReturn(expectedList)
            userRepository.fetchUserList()
            verify(mockedGithubService).fetchUserList()
            verify(mockedUserDao).insertOrReplaceList(expectedList.map {
                it.toDao()
            })
        }
    }

    @Test
    fun `fetchUserList should execute fetch user from retrofit service but no result that put into database`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val expectedList = emptyList<User>()
            whenever(mockedGithubService.fetchUserList()).thenReturn(expectedList)
            userRepository.fetchUserList()
            verify(mockedGithubService).fetchUserList()
            verify(mockedUserDao).insertOrReplaceList(expectedList.map {
                it.toDao()
            })
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

    @Test
    fun `getUserList should return list of user if there is value on user table`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val dummyUser: User = Dummy.createUser()
            val expectedList = listOf(dummyUser).map {
                it.toDao()
            }
            whenever(mockedUserDao.getUserList()).thenReturn(expectedList)
            val result = userRepository.getUserList().map {
                it.toDao()
            }
            verify(mockedUserDao).getUserList()
            assertEquals(expectedList, result)
        }
    }

    @Test
    fun `getUserList should return empty list of user if there is no value on user table`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val dummyUser: User = mock()
            val expectedList = emptyList<UserEntity>()
            whenever(mockedUserDao.getUserList()).thenReturn(expectedList)
            val result = userRepository.getUserList()
            verify(mockedUserDao).getUserList()
            assertEquals(expectedList, result)
        }
    }

    @Test
    fun `getUserList should return exception if get some error when query from database`() {
        coroutineTestRule.runBlockingErrorTest(
            testFunction = {
                whenever(mockedUserDao.getUserList()).thenThrow(MockitoException(RandomString.make(2)))
                userRepository.getUserList()
            },
            verifyFunction = {
                it is MockitoException
            }
        )
    }
}
