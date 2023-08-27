package ar.edu.unq.tpi.ciriaqui.exception

import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class TeacherNotFoundExceptionTest {
    @Test
    @DisplayName("The message of a TeacherNotFoundException is the expected")
    fun testTeacherNotFoundExceptionIsTheExpected(){
        val exception = TeacherNotFoundException(42L)
        assertEquals("Teacher with ID 42 not found", exception.message)
    }
}