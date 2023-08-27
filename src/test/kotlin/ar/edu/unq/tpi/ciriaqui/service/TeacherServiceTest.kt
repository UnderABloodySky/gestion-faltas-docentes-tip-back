package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherServiceTest {

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Mock
    private lateinit var teacherRepositoryMock: TeacherRepository

    private lateinit var teacherService: TeacherService
    private lateinit var teacherServiceImpl : TeacherService
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        teacherService = TeacherService(teacherRepository)
        teacherServiceImpl = TeacherService(teacherRepository)
    }

    @Test
    @DisplayName("TeacherService found a instance of teacher by ID 1 when existe a teacher with this ID")
    fun testTeacherServiceSaveAndFoundATeacherByID() {
        val aTeacherID = 1L
        val aTeacher = Teacher(id = aTeacherID, name = "Pepito")

        Mockito.`when`(teacherRepositoryMock.findById(aTeacherID)).thenReturn(Optional.of(aTeacher))

        val foundTeacher = teacherService.findTeacherById(aTeacherID)
        assertEquals("Pepito", foundTeacher.name)
    }

    @Test
    @DisplayName("TeacherService throws a TeacherNotFoundException when find by ID with a wrong ID")
    fun testTeacherServiceThrowsTeacherNotFoundException() {
        val incorrectID = 42L

        Mockito.`when`(teacherRepositoryMock.findById(incorrectID)).thenReturn(Optional.empty())

        org.junit.jupiter.api.assertThrows<TeacherNotFoundException> {
            teacherService.findTeacherById(incorrectID)
        }
    }

    @Test
    @DisplayName("TeacherService found a instance of teacher by ID 1 when existe a teacher with this ID")
    fun testTeacherServiceImplFoundATeacherByID() {
        val aTeacher = Teacher(name = "Pepito")

        teacherService.save(aTeacher)

        val foundTeacher = teacherService.findTeacherById(aTeacher.id!!)

        assertNotNull(foundTeacher)
        assertEquals(aTeacher.name, foundTeacher.name)
    }

    @Test
    @DisplayName("TeacherService throws a TeacherNotFoundException when find by ID with a wrong ID")
    fun testTeacherServiceThrowsTeacherNotFoundExceptionReally() {
        org.junit.jupiter.api.assertThrows<TeacherNotFoundException> {
            teacherServiceImpl.findTeacherById(555L)
        }
    }
}