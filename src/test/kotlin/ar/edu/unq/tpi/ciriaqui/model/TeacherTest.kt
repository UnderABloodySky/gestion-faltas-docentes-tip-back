package ar.edu.unq.tpi.ciriaqui.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class TeacherTest {

    private lateinit var teacher : Teacher

    @BeforeEach
    fun setUp() {
        teacher = Teacher(name = "Pepito")
    }

    @Test
    fun aTeacherHasAName() {
        assertEquals("Pepito",teacher.name)
    }

    @Test
    fun aTeacherHasNotAnID() {
        assertNull(teacher.id)
    }
}