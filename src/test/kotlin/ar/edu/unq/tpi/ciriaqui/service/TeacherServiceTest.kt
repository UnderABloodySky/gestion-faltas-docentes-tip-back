package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.DTO.LoginDTO
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectCredentialException
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
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
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
        teacherService = TeacherService(teacherRepositoryMock)
        teacherServiceImpl = TeacherService(teacherRepository)
    }

    @Test
    @DisplayName("TeacherService found a instance of teacher by ID 1 when existe a teacher with this ID")
    fun testTeacherServiceSaveAndFoundATeacherByID() {
        val aTeacherID = 1L
        val aTeacher = Teacher(name = "Pepito", email = "asd@gmail.com", password = "1234")

        Mockito.`when`(teacherRepositoryMock.findById(aTeacherID)).thenReturn(Optional.of(aTeacher))

        val foundTeacher = teacherService.findTeacherById(aTeacherID)
        assertEquals("Pepito", foundTeacher!!.name)
        assertEquals(aTeacher.id, foundTeacher.id)
        assertEquals(aTeacher.email, foundTeacher.email)
        assertEquals(aTeacher.password, foundTeacher.password)
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
        val aTeacher = Teacher(name = "Pepita", email = "asd", password = "asd")

        teacherServiceImpl.save(aTeacher)

        val foundTeacher = teacherServiceImpl.findTeacherById(aTeacher.id!!)

        assertEquals(aTeacher.name, foundTeacher!!.name)
        assertEquals(aTeacher.id, foundTeacher.id)
        assertEquals(aTeacher.email, foundTeacher.email)
        assertEquals(aTeacher.password, foundTeacher.password)
    }

    @Test
    @DisplayName("TeacherService throws a TeacherNotFoundException when find by ID with a wrong ID")
    fun testTeacherServiceThrowsTeacherNotFoundExceptionReally() {
        org.junit.jupiter.api.assertThrows<TeacherNotFoundException> {
            teacherServiceImpl.findTeacherById(555L)
        }
    }

    @Test
    @DisplayName("TeacherService found a instance of teacher by email when existe a teacher with this ID")
    fun testTeacherServiceImplFoundATeacherByEmail() {
        val aTeacherEmail = "asd@asd.com"
        val aTeacher = Teacher(name = "Pepita", email = aTeacherEmail, password = "asd")

        teacherServiceImpl.save(aTeacher)

        val foundTeacher = teacherServiceImpl.findTeacherByEmail(aTeacherEmail)

        assertEquals(aTeacher.name, foundTeacher!!.name)
        assertEquals(aTeacher.id, foundTeacher.id)
        assertEquals(aTeacher.email, foundTeacher.email)
        assertEquals(aTeacher.password, foundTeacher.password)
    }

    @Test
    @DisplayName("TeacherService login a teacher by email when password is ok")
    fun testTeacherServiceImplLoginATeacher() {
        val aTeacherEmail = "asd@asd.com"
        val aTeacherPassword = "asd"
        val aTeacher = Teacher(name = "Pepita", email = aTeacherEmail, password = "asd")

        teacherServiceImpl.save(aTeacher)

        val foundTeacher = teacherServiceImpl.login(LoginDTO(aTeacherEmail, aTeacherPassword))

        assertEquals(aTeacher.name, foundTeacher!!.name)
        assertEquals(aTeacher.password, foundTeacher.password)
        assertEquals(aTeacher.email, foundTeacher.email)
    }

    @Test
    @DisplayName("TeacherService throws a TeacherNotFoundException when try to login with an incorrect email")
    fun testTeacherServiceThrowsTeacherNotFoundExceptionWhenEmailIsWrong() {
        val aTeacher = Teacher(name = "Pepita", email = "saras@gmail.com", password = "asd")
        teacherRepository.save(aTeacher)
        org.junit.jupiter.api.assertThrows<IncorrectCredentialException> {
            teacherServiceImpl.login(LoginDTO("sarasa@gmail.com", "1234"))
        }
    }

    @Test
    @DisplayName("TeacherService throws a IncorrectCredentialsException when try to login with an incorrect password")
    fun testTeacherServiceThrowsTeacherNotFoundExceptionWhenPassWordIsWrong() {
        val password = "asd"
        val aTeacher = Teacher(name = "Pepita", email = "sarasa@gmail.com", password = password)
        teacherRepository.save(aTeacher)
        org.junit.jupiter.api.assertThrows<IncorrectCredentialException> {
            teacherServiceImpl.login(LoginDTO("sarasa@gmail.com", "1234"))
        }
    }
}