package comtest.ct.cd.zulfikar.repository.impl

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import comtest.ct.cd.zulfikar.CoroutineTestRule
import comtest.ct.cd.zulfikar.constant.WebServiceConfigConstant
import comtest.ct.cd.zulfikar.db.dao.UserDao
import comtest.ct.cd.zulfikar.network.GithubService
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.user.UserListOrderBy
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
import kotlin.random.Random

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

//    @Test
//    fun `fetchUserList should execute fetch user from retrofit service and put result into database`() {
//        coroutineTestRule.testDispatcher.runBlockingTest {
//            val dummyQuery = RandomString.make()
//            val dummyPage = Random.nextLong()
//            val dummyPerPage = Random.nextLong()
//            val dummyOrderBy = UserListOrderBy.NAME_ASC
//            val dummyUser: User = mock()
//
//            val expectedList = listOf(dummyUser.copy(
//                login = dummyQuery
//            ))
//            whenever(
//                mockedGithubService.fetchUserList(
//                    any(),
//                    any(),
//                    any(),
//                    any()
//                ).filter { true }
//            ).thenReturn(expectedList)
//            val result = userRepository.fetchUserList(
//                dummyQuery,
//                dummyOrderBy,
//                dummyPage,
//                dummyPerPage
//            )
//            verify(mockedGithubService).fetchUserList(
//                dummyQuery,
//                dummyOrderBy.key,
//                dummyPage,
//                dummyPerPage
//            )
//            assertEquals(expectedList, result)
//        }
//    }

    @Test
    fun `fetchUserList should execute fetch user from retrofit service but no result that put into database`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val dummyQuery = RandomString.make()
            val dummyPage = Random.nextLong()
            val dummyPerPage = Random.nextLong()
            val dummyOrderBy = UserListOrderBy.NAME_ASC
            val expectedList = emptyList<User>()
            whenever(
                mockedGithubService.fetchUserList(
                    any(),
                    any(),
                    any(),
                    any()
                )
            ).thenReturn(expectedList)
            val result = userRepository.fetchUserList(
                dummyQuery, dummyOrderBy,
                dummyPage, dummyPerPage
            )
            verify(mockedGithubService).fetchUserList(
                dummyQuery.toLowerCase(), dummyOrderBy.key, dummyPage, dummyPerPage
            )
            assertEquals(expectedList, result)
        }
    }

    @Test
    fun `fetchUserList should return exception when error response from webservice`() {
        coroutineTestRule.runBlockingErrorTest(
            testFunction = {
                val dummyQuery = RandomString.make()
                val dummyPage = Random.nextLong()
                val dummyPerPage = Random.nextLong()
                val expectedException = MockitoException(RandomString.make(2))
                whenever(
                    mockedGithubService.fetchUserList(
                        dummyQuery, WebServiceConfigConstant.SORT_ASC, dummyPage, dummyPerPage
                    )
                ).thenThrow(expectedException)
                userRepository.fetchUserList(
                    dummyQuery, UserListOrderBy.NAME_ASC,
                    dummyPage, dummyPerPage
                )
            },
            verifyFunction = {
                it is MockitoException
            }
        )
    }
}
