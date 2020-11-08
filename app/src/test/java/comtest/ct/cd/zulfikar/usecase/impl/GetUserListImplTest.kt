package comtest.ct.cd.zulfikar.usecase.impl

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import comtest.ct.cd.zulfikar.CoroutineTestRule
import comtest.ct.cd.zulfikar.Dummy
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.usecase.GetUserList
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

class GetUserListImplTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(
        TestCoroutineDispatcher()
    )

    @Mock
    private lateinit var mockUserRepository: UserRepository

    private lateinit var getUserList: GetUserList

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserList = GetUserListImpl(
            userRepository = mockUserRepository
        )
    }

    @Test
    fun `execute should get data when data available`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val user = Dummy.createUser()
            val expected = listOf(user)
            whenever(mockUserRepository.getUserList()).thenReturn(expected)
            val result = getUserList.execute()
            verify(mockUserRepository).getUserList()
            assertEquals(expected, result)
        }
    }

    @Test
    fun `execute should get error when data not available`() {
        val expected = MockitoException(RandomString.make(2))
        coroutineTestRule.runBlockingErrorTest(
            testFunction = {
                whenever(mockUserRepository.getUserList()).thenThrow(expected)
                getUserList.execute()
            },
            verifyFunction = {
                it is MockitoException
            })
    }
}
