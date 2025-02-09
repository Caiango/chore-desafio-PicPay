import com.picpay.domain.user.User
import com.pipcpay.local.room.UserDao
import com.pipcpay.local.room.UserLocalProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserLocalProviderTest {


    private lateinit var userDao: UserDao
    private lateinit var userLocalProvider: UserLocalProvider

    @Before
    fun setUp() {
        userDao = mockk(relaxed = true)
        userLocalProvider = UserLocalProvider(userDao)
    }

    @Test
    fun shouldInsertUsersIntoDatabase() = runBlocking {

        val users = listOf(
            User(id = 1, name = "John", img = "img1", username = "Jhonny"),
            User(id = 2, name = "Caio", img = "img2", username = "Cai")
        )

        userLocalProvider.insertUsers(users)

        users.forEach {
            coVerify { userDao.insert(it) }
        }
    }

    @Test
    fun shouldReturnAllUsersFromTheDatabase() = runBlocking {

        val expectedUsers = listOf(
            User(id = 1, name = "John", img = "img1", username = "Jhonny"),
            User(id = 2, name = "Caio", img = "img2", username = "Cai")
        )

        coEvery { userDao.getAllUsers() } returns expectedUsers

        val users = userLocalProvider.getAllUsers()

        assertEquals(expectedUsers, users)
        coVerify { userDao.getAllUsers() }
    }
}
