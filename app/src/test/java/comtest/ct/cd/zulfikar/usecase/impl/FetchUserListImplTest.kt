package comtest.ct.cd.zulfikar.usecase.impl

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import comtest.ct.cd.zulfikar.CoroutineTestRule
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.User
import comtest.ct.cd.zulfikar.usecase.FetchUserList
import comtest.ct.cd.zulfikar.user.UserListOrderBy
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import net.bytebuddy.utility.RandomString
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import kotlin.random.Random

class FetchUserListImplTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(
        TestCoroutineDispatcher()
    )

    @Mock
    private lateinit var mockedUserRepository: UserRepository

    private lateinit var fetchUserList: FetchUserList

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fetchUserList = FetchUserListImpl(
            userRepository = mockedUserRepository
        )
    }

    @Test
    fun `execute should calling fetchUser function from userRepository`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val dummyQuery = RandomString.make()
            val dummyPage = Random.nextLong()
            val dummyOrderBy = UserListOrderBy.NAME_ASC
            val expectedResult = listOf<User>()
            whenever(
                mockedUserRepository.fetchUserList(
                    any(),
                    any(),
                    any(),
                    any()
                )
            ).thenReturn(expectedResult)
            val result = fetchUserList.execute(
                dummyQuery, dummyOrderBy,
                dummyPage
            )
            verify(mockedUserRepository).fetchUserList(
                dummyQuery, dummyOrderBy,
                dummyPage
            )
            Assert.assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `execute should return error when get invalid value from service`() {
        coroutineTestRule.runBlockingErrorTest(
            testFunction = {
                val dummyQuery = RandomString.make()
                val dummyPage = Random.nextLong()
                val dummyPerPage = Random.nextLong()
                val expectedResult = listOf<User>()
                whenever(
                    mockedUserRepository.fetchUserList(
                        any(),
                        any(),
                        any(),
                        any()
                    )
                ).thenThrow(
                    MockitoException(
                        RandomString.make()
                    )
                )
                fetchUserList.execute(
                    dummyQuery, UserListOrderBy.NAME_ASC,
                    dummyPage
                )
            },
            verifyFunction = {
                it is MockitoException
            }
        )
    }
}
