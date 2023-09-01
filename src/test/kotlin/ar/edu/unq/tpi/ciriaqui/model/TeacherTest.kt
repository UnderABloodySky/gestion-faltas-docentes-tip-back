package ar.edu.unq.tpi.ciriaqui.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class TeacherTest {
    private lateinit var aTeacher : Teacher

    @BeforeEach
    fun setUp() {
        aTeacher = Teacher(name = "Pepito", password = "asd123", email="tea@asd.cp")
    }

    @Test
    @DisplayName("A teacher has a name")
    fun aTeacherHasAName() {
        assertEquals("Pepito", aTeacher.name)
    }

    @Test
    @DisplayName("A teacher has an ID")
    fun aTeacherHasNotAnID() {
        assertNull(aTeacher.id)
    }
}