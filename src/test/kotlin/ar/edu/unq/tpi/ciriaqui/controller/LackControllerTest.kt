package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.DTO.LackDTO
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import ar.edu.unq.tpi.ciriaqui.service.LackService
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LackControllerTest {
    @Autowired
    private lateinit var teacherService: TeacherService
    @Autowired
    private lateinit var lackService: LackService
    private lateinit var lackController: LackController
    private lateinit var aParticularLackDTO: LackDTO
    private lateinit var aTeacher: Teacher
    private lateinit var savedLack: Lack
    @BeforeEach
    fun setUp(){
        aTeacher = Teacher(name = "El que falto", email ="faltante@fgh.fk", password="12345")
        teacherService.save(aTeacher)
        lackController = LackController(lackService, teacherService)
        aParticularLackDTO = LackDTO("PARTICULAR", "2023-12-31", aTeacher.id!!)
        savedLack = lackController.save(aParticularLackDTO).body!!
    }

    @Test
    @DisplayName("A lack can be save")
    fun testALackCanBeSave(){
        val foundLack = lackController.findLackById(savedLack.id!!).body
        assertEquals(savedLack.article, foundLack!!.article)
        assertEquals(LocalDate.parse("2023-12-31", DateTimeFormatter.ISO_LOCAL_DATE), foundLack.beginDate)
        assertEquals(aTeacher.id!!, foundLack.teacher?.id!!)
    }

    @Test
    @DisplayName("A lack can´t be save when date is previous to now")
    fun testALackCannotBeSave(){
        val wrongDateLackDTO = LackDTO("CONTEST", "1987-04-14", aTeacher.id!!)
        val response = lackController.save(wrongDateLackDTO)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @DisplayName("A lack can´t be save when Article is wrong")
    fun testALackCannotBeSaveWhenArticleIsWrong(){
        val wrongDateLackDTO = LackDTO("ASD", "2024-04-14", aTeacher.id!!)
        val response = lackController.save(wrongDateLackDTO)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @DisplayName("A lack can´t be save when Date cannot be parse")
    fun testALackCannotBeSaveWhenDateIsWrong(){
        val wrongDateLackDTO = LackDTO("CONTEST", "fghj", aTeacher.id!!)
        val response = lackController.save(wrongDateLackDTO)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @DisplayName("A lack can´t be save when teacher id is wrong")
    fun testALackCannotBeSaveWhenIDIsWrong(){
        val wrongDateLackDTO = LackDTO("CONTEST", "2025-01-01", 420000L)
        val response = lackController.save(wrongDateLackDTO)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}