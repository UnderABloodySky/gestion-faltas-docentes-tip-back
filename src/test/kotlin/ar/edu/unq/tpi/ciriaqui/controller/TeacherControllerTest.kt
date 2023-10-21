package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dto.LoginDTO
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeacherControllerTest {
    @Autowired
    private lateinit var teacherService: TeacherService
    @Autowired
    private lateinit var teacherController: TeacherController
    private lateinit var teacher: Teacher

    @BeforeEach
    fun setUp(){
        teacher = Teacher(name="Pepito", email="teachercontrollerOK@asd.com", password="1234")
        teacherService.save(teacher)
    }

    @Test
    @DisplayName("TeacherController returns a Teacher when login is OK")
    fun testAceptedLogin(){
        val response = teacherController.login(LoginDTO("teachercontrollerOK@asd.com", "1234"))
        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        val foundTeacher = response.body
        assertEquals("teacher", foundTeacher)
        //assertEquals(teacher.name, foundTeacher!!.name)
        //assertEquals(teacher.password, foundTeacher.password)
        //assertEquals(teacher.email, foundTeacher.email)
        //assertEquals(teacher.id, foundTeacher.id)
    }

    /*
    @Test
    @DisplayName("TeacherController returns a badRequest when password is wrong")
    fun testtestTestControllerReturnABadRequestWhenPasswordIsWrong(){
        val response = teacherController.login(LoginDTO("teachercontrollerA@asd.com", "asd"))
        assertNotNull(response)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertNull(response.body)
    }

    @Test
    @DisplayName("TeacherController returns a badRequest when email is wrong")
    fun testTestControllerReturnABadRequestWhenEmailIsWrong(){
        val response = teacherController.login(LoginDTO("teachercontrollerA@asd.com", "asd"))
        assertNotNull(response)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }
    */
}