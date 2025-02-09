package remote

import com.picpay.remote.UserRemote
import com.picpay.remote.toDomain
import org.junit.Test
import kotlin.test.assertEquals

class UserRemoteTest {

    @Test
    fun shouldMapUserRemoteToUserCorrectly() {

        val userRemote = UserRemote(
            img = "https://example.com/image.jpg",
            name = "Caio",
            id = 1,
            username = "Caiango"
        )

        val user = userRemote.toDomain()

        assertEquals(user.img, "https://example.com/image.jpg")
        assertEquals(user.name, "Caio")
        assertEquals(user.id, 1)
        assertEquals(user.username, "Caiango")
    }

    @Test
    fun shouldHandleNullFieldsInUserRemote() {

        val userRemote = UserRemote(
            img = null,
            name = null,
            id = null,
            username = null
        )

        val user = userRemote.toDomain()

        assertEquals(user.img, "")
        assertEquals(user.name, "")
        assertEquals(user.id, 0)
        assertEquals(user.username, "")
    }
}
