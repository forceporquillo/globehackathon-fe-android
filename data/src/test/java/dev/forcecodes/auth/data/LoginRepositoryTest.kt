package dev.forcecodes.auth.data

import dev.forcecodes.auth.data.api.LoginAuthNetworkCall
import dev.forcecodes.auth.data.pref.TokenStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DefaultLoginRepositoryTest {

    private lateinit var loginRepository: DefaultLoginRepository
    private lateinit var fakeLoginAuthNetworkCall: FakeLoginAuthNetworkCall
    private lateinit var fakeTokenStorage: FakeTokenStorage

    @Before
    fun setUp() {

        Dispatchers.setMain(StandardTestDispatcher())

        fakeLoginAuthNetworkCall = FakeLoginAuthNetworkCall()
        fakeTokenStorage = FakeTokenStorage()
        loginRepository = DefaultLoginRepository(fakeLoginAuthNetworkCall, fakeTokenStorage)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login should return success message and save token on valid credentials`() = runBlocking {
        // Arrange
        val email = "helloworld@example.com"
        val password = "test123"
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlNldmVuIFNldmVuIiwiaWF0IjoxNTE2MjM5MDIyfQ.xxlfPVaVNik4-8So5JGj-duilHnBF7Me7mMOher1hf4"
        fakeLoginAuthNetworkCall.setSuccessResult(token)

        // Act
        val result = loginRepository.login(email, password)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals("Successfully logged in!", result.getOrNull())
        assertEquals(token, fakeTokenStorage.jwtToken)
    }

    @Test
    fun `login should return failure on invalid credentials`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "wrong_password"
        val exception = IllegalArgumentException("Invalid credentials")
        fakeLoginAuthNetworkCall.setFailureResult(exception)

        // Act
        val result = loginRepository.login(email, password)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
        assertNull(fakeTokenStorage.jwtToken) // Ensure token is not saved
    }

    private class FakeLoginAuthNetworkCall : LoginAuthNetworkCall() {

        private var result: Result<String> = Result.failure(RuntimeException("Not initialized"))

        fun setSuccessResult(token: String) {
            result = Result.success(token)
        }

        fun setFailureResult(exception: Throwable) {
            result = Result.failure(exception)
        }

    }

    // fakes
    private class FakeTokenStorage : TokenStorage {

        var jwtToken: String? = null

        override fun saveToken(token: String) {
            this.jwtToken = token
        }

        override fun getToken(): String? = jwtToken

        override fun clearToken() {
            jwtToken = null
        }
    }
}
