package ar.edu.unq.tpi.ciriaqui.model

import org.junit.jupiter.api.Assertions.*
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

    @Test
    @DisplayName("A Teacher can return true when its password is rigth")
    fun testATeacherCanReturnTrueWhenItsPasswordIsRigth(){
        assertTrue(aTeacher.isCorrectPassword(aTeacher.password))
    }

    @Test
    @DisplayName("A Teacher can return false when its password is wrong")
    fun testATeacherCanReturnFalseWhenItsPasswordIsWrong(){
        assertFalse(aTeacher.isCorrectPassword("aTeacher.password"))
    }

    @Test
    @DisplayName("Teacher returns its equals to itself")
    fun testTeacherReturnThatIsEqualsToOtherTeacher(){
        assertTrue(aTeacher == aTeacher)
    }

    @Test
    @DisplayName("Teacher returns itsn't equals to other teacher")
    fun testTeacherReturnThatNotIsEqualsToOtherTeacher(){
        val otherTeacher = Teacher(name ="", email= "", password= "")
        assertFalse(aTeacher == otherTeacher)
    }

    @Test
    @DisplayName("A teacher understands toString()")
    fun testAteacherUnderstandtoString(){
        assertEquals("Teacher(id = null )", aTeacher.toString())
    }
}