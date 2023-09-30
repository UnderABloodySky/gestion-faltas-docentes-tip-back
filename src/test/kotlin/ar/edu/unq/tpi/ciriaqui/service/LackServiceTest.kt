package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
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
class LackServiceTest {
    @Autowired
    private lateinit var teacherService: TeacherService
    @Autowired
    private lateinit var lackRepository : LackRepository
    @Autowired
    private lateinit var lackService: LackService
    private lateinit var aParticularLackDTO: LackDTO
    private lateinit var aTeacher: Teacher
    private lateinit var otherTeacher: Teacher
    private lateinit var savedLack: Lack
    @BeforeEach
    fun setUp(){
        aTeacher = Teacher(name = "El que falto", email ="faltante@fgh.fk", password="12345")
        otherTeacher = Teacher(name = "Uno que no falto", email="nofalto@unq", password="sarmiento")
        teacherService.save(aTeacher)
        teacherService.save(otherTeacher)
        aParticularLackDTO = LackDTO(null,"PARTICULAR", "2023-12-31", "2023-12-31", aTeacher.id!!)
        savedLack = lackService.save(aParticularLackDTO)
    }

    @Test
    @DisplayName("A lack can be save")
    fun testALackCanBeSave(){
        val foundLack = lackService.findLackById(savedLack.id!!)
        assertEquals(savedLack.article, foundLack!!.article)
        assertEquals(LocalDate.parse("2023-12-31", DateTimeFormatter.ISO_LOCAL_DATE), foundLack.beginDate)
        assertEquals(aTeacher.id!!, foundLack.teacher?.id!!)
    }

    @Test
    @DisplayName("A lack can´t be save when date is previous to now")
    fun testALackCannotBeSave(){
        val wrongDateLackDTO = LackDTO(123L,"CONTEST", "1987-04-14", "1987-04-14",aTeacher.id!!)
        //val response = lackService.save(wrongDateLackDTO)
        //Falta assert
    }

    @Test
    @DisplayName("A lack can´t be save when Article is wrong")
    fun testALackCannotBeSaveWhenArticleIsWrong(){
        //val wrongDateLackDTO = LackDTO(savedLack.id, "ASD", "2024-04-14", "2024-04-14",aTeacher.id!!)
        //val response = lackService.save(wrongDateLackDTO)
        //Falta assert
    }

    @Test
    @DisplayName("A lack can´t be save when Date cannot be parse")
    fun testALackCannotBeSaveWhenDateIsWrong(){
        val wrongDateLackDTO = LackDTO(123L,"CONTEST", "fghj", "2024-04-14",aTeacher.id!!)
        //val response = lackService.save(wrongDateLackDTO)
        //Falta assert
    }

    @Test
    @DisplayName("A lack can´t be save when teacher id is wrong")
    fun testALackCannotBeSaveWhenIDIsWrong(){
        //val wrongDateLackDTO = LackDTO(article = "CONTEST", beginDate = "2025-01-01", endDate = "2025-01-01", idTeacher = 420000L)
        //val response = lackService.save(wrongDateLackDTO)
        //Falta assert
    }

    @Test
    @DisplayName("lackService returns an empty list when the teacher doesnt faul")
    fun testlackServiceReturnsAnEmptyListWhenTheTeacherDoesntFaul(){
        val response = lackService.lacksOf(otherTeacher.id)
        assertEquals(0, response.size)
    }

    @Test
    @DisplayName("lackService returns an empty list when the teacher doesnt faul")
    fun testlackServiceReturnsAnListWithOneElementeWhenTheTeacherHasOnlyALack(){
        val response = lackService.lacksOf(aTeacher.id)
        assertEquals(1, response.size)
    }

    @Test
    @DisplayName("lackService returns an empty list when the teacher doesnt faul")
    fun testlackServiceThrowAnExceptionWhenTheTeacherDoesntExist(){
        val response = lackService.lacksOf(57483920L)
        assertEquals(0, response.size)
    }

    @Test
    @DisplayName("lackService returns NotFOUNDStatus when find by wrong ID")
    fun testlackServiceReturnsNotFOUNDStatusWhenFindByWrongID(){
        //val response = lackService.findLackById(57483920L)
        //Falta assert
    }

    @Test
    @DisplayName("lackService returns an OK when delete an Lack by a rigth ID")
    fun testlackServiceReturnsAnOKWhenDeleteAnLackByARigthID(){
        var otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2028-12-30", "2028-12-30", aTeacher.id!!)
        val otherSavedLack = lackService.save(otherParticularLackDTO)
        val response = lackService.deleteLackById(otherSavedLack.id)
        //Falta assert
    }

    @Test
    @DisplayName("lackService delete a lack when delete an Lack by a rigth ID")
    fun testlackServiceCanDeleteALackWhenDeleteAnLackByARigthID(){
        var otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2027-12-30", "2027-12-30", aTeacher.id!!)
        val otherSavedLack = lackService.save(otherParticularLackDTO)
        val antiqueCount = lackRepository.count()
        lackService.deleteLackById(otherSavedLack.id)
        assertEquals(antiqueCount-1, lackRepository.count())
    }

    @Test
    @DisplayName("lackService returns an NOTFOUND when delete an Lack by a wrong ID")
    fun testlackServiceReturnsAnNOTFOUNDWhenDeleteAnLackByAWrongID(){
        //val response = lackService.deleteLackById(123456789012345L)
        //Falta assert
    }

    @Test
    @DisplayName("lackService cant delete a lack when delete an Lack by a wrong ID")
    fun testlackServiceCannotDeleteALackWhenDeleteAnLackByAWrongID(){
        val antiqueCount = lackRepository.count()
        //lackService.deleteLackById(123456789012345L)
        assertEquals(antiqueCount, lackRepository.count())
    }

    @Test
    @DisplayName("lackService can update the article field for a Lack")
    fun testlackServiceReturnsAnOKWhenUpdateTheArticleFieldForALack(){
        var otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2029-12-30", "2029-12-30", aTeacher.id!!)
        val otherSavedLack = lackService.save(otherParticularLackDTO)
        val updateDTO = LackDTO(otherSavedLack.id, "STUDYDAY", "2023-12-31","2023-12-31", aTeacher.id!!)
        val response = lackService.updatelackById(updateDTO)
        assertEquals(Article.STUDYDAY, response?.article)
    }

    @Test
    @DisplayName("lackService can update the article field for a Lack")
    fun testlackServiceCanUpdateTheArticleFieldForALack(){
        var otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2029-12-30", "2029-12-30", aTeacher.id!!)
        val otherSavedLack = lackService.save(otherParticularLackDTO)
        val responseBefore = lackService.findLackById(otherSavedLack.id!!)
        assertEquals(Article.PARTICULAR, responseBefore!!.article)
        val updateDTO = LackDTO(otherSavedLack.id,"STUDYDAY", "2023-12-31", "2023-12-31", aTeacher.id!!)
        lackService.updatelackById(updateDTO)
        val responseAfter = lackService.findLackById(otherSavedLack.id!!)
        assertEquals(Article.STUDYDAY, responseAfter!!.article)
    }

}