package remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.picpay.domain.screen.ScreenStatus
import com.picpay.remote.ServiceProvider
import com.picpay.remote.UserRemote
import com.picpay.remote.UserRemoteProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserRemoteProviderTest {

    private lateinit var mockService: ServiceProvider
    private lateinit var mockContext: Context
    private lateinit var userRemoteProvider: UserRemoteProvider

    @Before
    fun setUp() {
        mockService = mockk(relaxed = true)
        mockContext = mockk(relaxed = true)

        val connectivityManager = mockk<ConnectivityManager>()
        val networkCapabilities = mockk<NetworkCapabilities>()

        coEvery { mockContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        coEvery { connectivityManager.activeNetwork } returns null
        coEvery { connectivityManager.getNetworkCapabilities(null) } returns networkCapabilities

        userRemoteProvider = UserRemoteProvider(mockService, mockContext)
    }

    @Test
    fun shouldReturnErrorWhenNetworkIsUnavailable() = runBlocking {

        val connectivityManager = mockk<ConnectivityManager>()
        val networkCapabilities = mockk<NetworkCapabilities>()


        every { mockContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns null
        every { connectivityManager.getNetworkCapabilities(null) } returns networkCapabilities
        every { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns false

        val result = userRemoteProvider.getUsers()

        assertNotNull(result)

        assertTrue(result.status is ScreenStatus.Error)
        assertNull(result.userList)
    }

    @Test
    fun shouldReturnUsersWhenNetworkIsAvailableAndResponseIsSuccessful() = runBlocking {

        val connectivityManager = mockk<ConnectivityManager>()
        val networkCapabilities = mockk<NetworkCapabilities>()

        every { mockContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns mockk()

        every { connectivityManager.getNetworkCapabilities(any()) } returns networkCapabilities
        every { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true

        val mockUsers =
            listOf(UserRemote(img = "img", name = "John Doe", id = 1, username = "john"))
        val mockResponse = Response.success(mockUsers)
        coEvery { mockService.getUsers() } returns mockResponse

        val result = userRemoteProvider.getUsers()

        assertNotNull(result)
        assertEquals(ScreenStatus.Ready::class.java, result.status::class.java)
        assertNotNull(result.userList)
        assertEquals(result.userList?.size, 1)
    }


    @Test
    fun shouldReturnErrorWhenNetworkIsAvailableButResponseIsNotSuccessful() =
        runBlocking {
            val connectivityManager = mockk<ConnectivityManager>()
            val networkCapabilities = mockk<NetworkCapabilities>()
            val network = mockk<Network>()

            coEvery { mockContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
            coEvery { connectivityManager.activeNetwork } returns network
            coEvery { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
            coEvery { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true

            val mockResponse =
                Response.error<List<UserRemote>>(404, okhttp3.ResponseBody.create(null, ""))
            coEvery { mockService.getUsers() } returns mockResponse

            val result = userRemoteProvider.getUsers()

            assertNotNull(result)
            assertTrue(result.status is ScreenStatus.Error)
            assertNull(result.userList)
        }
}
