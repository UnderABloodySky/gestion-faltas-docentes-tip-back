package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import ar.edu.unq.tpi.ciriaqui.service.InstructService
import ar.edu.unq.tpi.ciriaqui.service.LackService
import ar.edu.unq.tpi.ciriaqui.service.SubjectService
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
    private lateinit var lackRepository : LackRepository
    @Autowired
    private lateinit var lackService: LackService
    @Autowired
    private lateinit var subjectService: SubjectService
    @Autowired
    private lateinit var instructService: InstructService

    private lateinit var lackController: LackController
    private lateinit var aParticularLackDTO: LackDTO
    private lateinit var aTeacher: Teacher
    private lateinit var otherTeacher: Teacher
    private lateinit var savedLack: Lack
    @BeforeEach
    fun setUp(){
        aTeacher = Teacher(name = "El que falto", email ="faltante@fgh.fk", password="12345")
        otherTeacher = Teacher(name = "Uno que no falto", email="nofalto@unq", password="sarmiento")
        lackService = LackService(teacherService, lackRepository)
        teacherService.save(aTeacher)
        teacherService.save(otherTeacher)
        lackController = LackController(lackService, teacherService, subjectService, instructService)
        aParticularLackDTO = LackDTO(null,"PARTICULAR", "2023-12-31", "2023-12-31", aTeacher.id!!)
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
        val wrongDateLackDTO = LackDTO(123L,"CONTEST", "1987-04-14", "1987-04-14",aTeacher.id!!)
        val response = lackController.save(wrongDateLackDTO)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @DisplayName("A lack can´t be save when Article is wrong")
    fun testALackCannotBeSaveWhenArticleIsWrong(){
        val wrongDateLackDTO = LackDTO(savedLack.id, "ASD", "2024-04-14", "2024-04-14",aTeacher.id!!)
        val response = lackController.save(wrongDateLackDTO)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @DisplayName("A lack can´t be save when Date cannot be parse")
    fun testALackCannotBeSaveWhenDateIsWrong(){
        val wrongDateLackDTO = LackDTO(123L,"CONTEST", "fghj", "2024-04-14",aTeacher.id!!)
        val response = lackController.save(wrongDateLackDTO)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @DisplayName("A lack can´t be save when teacher id is wrong")
    fun testALackCannotBeSaveWhenIDIsWrong(){
        val wrongDateLackDTO = LackDTO(article = "CONTEST", beginDate = "2025-01-01", endDate = "2025-01-01", idTeacher = 420000L)
        val response = lackController.save(wrongDateLackDTO)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }


    @Test
    @DisplayName("LackController returns an empty list when the teacher doesnt faul")
    fun testLackControllerReturnsAnEmptyListWhenTheTeacherDoesntFaul(){
        val response = lackController.lacksOf(otherTeacher.id!!, "2020-01-01", "2025-12-31").body
        assertEquals(0, response?.size)
    }

    @Test
    @DisplayName("LackController returns an empty list when the teacher doesnt faul")
    fun testLackControllerReturnsAnListWithOneElementeWhenTheTeacherHasOnlyALack(){
        val response = lackController.lacksOf(aTeacher.id!!).body
        assertEquals(1, response?.size)
    }

    @Test
    @DisplayName("LackController returns an empty list when the teacher doesnt faul")
    fun testLackControllerThrowAnExceptionWhenTheTeacherDoesntExist(){
        val response = lackController.lacksOf(57483920L, "", "")
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    @DisplayName("LackController returns NotFOUNDStatus when find by wrong ID")
    fun testLackControllerReturnsNotFOUNDStatusWhenFindByWrongID(){
        val response = lackController.findLackById(57483920L)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    @DisplayName("LackController returns an OK when delete an Lack by a rigth ID")
    fun testLackControllerReturnsAnOKWhenDeleteAnLackByARigthID(){
        val otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2026-12-30", "2026-12-30", aTeacher.id!!)
        val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
        val response = lackController.deleteLack(otherSavedLack.id)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    @DisplayName("LackController delete a lack when delete an Lack by a rigth ID")
    fun testLackControllerCanDeleteALackWhenDeleteAnLackByARigthID(){
        val otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2023-12-30", "2023-12-30", aTeacher.id!!)
        val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
        val antiqueCount = lackRepository.count()
        lackController.deleteLack(otherSavedLack.id)
        assertEquals(antiqueCount-1, lackRepository.count())
    }

    @Test
    @DisplayName("LackController returns an NOTFOUND when delete an Lack by a wrong ID")
    fun testLackControllerReturnsAnNOTFOUNDWhenDeleteAnLackByAWrongID(){
        val response = lackController.deleteLack(123456789012345L)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    @DisplayName("LackController cant delete a lack when delete an Lack by a wrong ID")
    fun testLackControllerCannotDeleteALackWhenDeleteAnLackByAWrongID(){
        val antiqueCount = lackRepository.count()
        lackController.deleteLack(123456789012345L)
        assertEquals(antiqueCount, lackRepository.count())
    }


    @Test
    @DisplayName("LackController can update the article field for a Lack")
    fun testLackControllerReturnsAnOKWhenUpdateTheArticleFieldForALack(){
        val otherParticularLackDTO = LackDTO(null,"PARTICULAR", "2025-12-31", "2025-12-31", aTeacher.id!!)
        val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
        val updateDTO = LackDTO(otherSavedLack.id, "STUDYDAY", "2025-12-31","2025-12-31", aTeacher.id!!)
        val response = lackController.updateLackById(updateDTO)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    @DisplayName("LackController can update the article field for a Lack")
    fun testLackControllerCanUpdateTheArticleFieldForALack() {
        val otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2024-12-31", "2024-12-31", aTeacher.id!!)
        val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
        otherParticularLackDTO.id = otherSavedLack.id!!
        val responseBefore = lackController.findLackById(otherSavedLack.id!!)
        assertEquals(Article.PARTICULAR, responseBefore.body!!.article)
        val updateDTO = LackDTO(otherSavedLack.id, "STUDYDAY", "2024-12-31", "2024-12-31", aTeacher.id!!)
        lackController.updateLackById(updateDTO)
        val responseAfter = lackController.findLackById(otherSavedLack.id!!)
        assertEquals(Article.STUDYDAY, responseAfter.body!!.article)
    }

    @Test
    @DisplayName("LackController can update the begin date of a Lack")
    fun testLackControllerCanUpdateTheBeginDateForALack() {
        val otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2024-12-31", "2024-12-31", aTeacher.id!!)
        val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
        otherParticularLackDTO.id = otherSavedLack.id!!
        val responseBefore = lackController.findLackById(otherSavedLack.id!!)
        assertEquals(Article.PARTICULAR, responseBefore.body!!.article)
        val updateDTO = LackDTO(otherSavedLack.id, "STUDYDAY", "2024-12-30", "2024-12-31", aTeacher.id!!)
        lackController.updateLackById(updateDTO)
        val responseAfter = lackController.findLackById(otherSavedLack.id!!)
        assertEquals(Article.STUDYDAY, responseAfter.body!!.article)
        assertEquals(LocalDate.parse("2024-12-30", DateTimeFormatter.ISO_LOCAL_DATE), responseAfter.body!!.beginDate)
    }

    @Test
    @DisplayName("LackController can update the End date of a Lack")
    fun testLackControllerCanUpdateTheEndDateForALack() {
        val otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2024-12-31", "2024-12-31", aTeacher.id!!)
        val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
        otherParticularLackDTO.id = otherSavedLack.id!!
        val responseBefore = lackController.findLackById(otherSavedLack.id!!)
        assertEquals(Article.PARTICULAR, responseBefore.body!!.article)
        val updateDTO = LackDTO(otherSavedLack.id, "STUDYDAY", "2024-12-30", "2025-01-01", aTeacher.id!!)
        lackController.updateLackById(updateDTO)
        val responseAfter = lackController.findLackById(otherSavedLack.id!!)
        assertEquals(Article.STUDYDAY, responseAfter.body!!.article)
        assertEquals(LocalDate.parse("2025-01-01", DateTimeFormatter.ISO_LOCAL_DATE), responseAfter.body!!.endDate)
    }

    @Test
    @DisplayName("LackController can update the Begin and the End date of a Lack")
    fun testLackControllerCanUpdateTheBeginDateAndTheEndDateForALack() {
        val otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2024-12-31", "2024-12-31", aTeacher.id!!)
        val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
        otherParticularLackDTO.id = otherSavedLack.id!!
        val responseBefore = lackController.findLackById(otherSavedLack.id!!)
        assertEquals(Article.PARTICULAR, responseBefore.body!!.article)
        val updateDTO = LackDTO(otherSavedLack.id, "STUDYDAY", "2024-12-19", "2024-12-30", aTeacher.id!!)
        lackController.updateLackById(updateDTO)
        val responseAfter = lackController.findLackById(otherSavedLack.id!!)
        assertEquals(Article.STUDYDAY, responseAfter.body!!.article)
        assertEquals(LocalDate.parse("2024-12-19", DateTimeFormatter.ISO_LOCAL_DATE), responseAfter.body!!.beginDate)
        assertEquals(LocalDate.parse("2024-12-30", DateTimeFormatter.ISO_LOCAL_DATE), responseAfter.body!!.endDate)
    }

    @Test
    @DisplayName("LackController can't update the Begin date of a Lack")
    fun testLackControllerCantUpdateTheBeginDateOfALack() {
        val otherParticularLackDTO0 = LackDTO(null, "PARTICULAR", "2024-12-15", "2024-12-20", aTeacher.id!!)
        val otherParticularLackDTO1 = LackDTO(null, "PARTICULAR", "2024-12-21", "2024-12-30", aTeacher.id!!)
        val otherSavedLack0 = lackController.save(otherParticularLackDTO0).body!!
        val otherSavedLack1 = lackController.save(otherParticularLackDTO1).body!!
        otherParticularLackDTO0.id = otherSavedLack0.id!!
        otherParticularLackDTO1.id = otherSavedLack1.id!!

        val updateDTO = LackDTO(otherSavedLack1.id, "STUDYDAY", "2024-12-18", "2024-12-29", aTeacher.id!!)
        val response = lackController.updateLackById(updateDTO)

        assertEquals(HttpStatus.CONFLICT, response.statusCode)
    }
}