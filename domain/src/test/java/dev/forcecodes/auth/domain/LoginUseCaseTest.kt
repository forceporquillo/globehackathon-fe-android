//package dev.forcecodes.auth.domain
//
//import kotlinx.coroutines.runBlocking
//import org.junit.Assert.*
//import org.junit.Test
//
//class LoginUseCaseTest {
//
//    @Test
//    fun `invoke should return success when loginRepository returns success`() = runBlocking {
//        // Arrange
//        val fakeLoginRepository = FakeLoginRepository(Result.success("Login Successful"))
////        val loginUseCase = LoginUseCase(fakeLoginRepository)
//
//        // Act
//        val result = loginUseCase("test@example.com", "password123")
//
//        // Assert
//        assertTrue(result.isSuccess)
//        assertEquals("Login Successful", result.getOrNull())
//    }
//
//    @Test
//    fun `invoke should return failure when loginRepository returns failure`() = runBlocking {
//        // Arrange
//        val exception = RuntimeException("Login Failed")
//        val fakeLoginRepository = FakeLoginRepository(Result.failure(exception))
//        val loginUseCase = LoginUseCase(fakeLoginRepository)
//
//        // Act
//        val result = loginUseCase("test@example.com", "wrong_password")
//
//        // Assert
//        assertTrue(result.isFailure)
//        assertEquals(exception, result.exceptionOrNull())
//    }
//
//    // Fake implementation of LoginRepository
//    private class FakeLoginRepository(private val result: Result<String>) : LoginRepository {
//        override suspend fun login(email: String, password: String): Result<String> {
//            return result
//        }
//    }
//}
