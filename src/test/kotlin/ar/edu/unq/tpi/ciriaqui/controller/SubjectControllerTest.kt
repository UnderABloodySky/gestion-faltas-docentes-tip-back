package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dto.SubjectDTO
import ar.edu.unq.tpi.ciriaqui.model.Subject
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import ar.edu.unq.tpi.ciriaqui.service.SubjectService
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubjectControllerTest {
        @Autowired
        private lateinit var teacherService: TeacherService
        @Autowired
        private lateinit var subjectService: SubjectService
        private lateinit var subjectController: SubjectController
        private lateinit var aTeacher: Teacher
        private lateinit var otherTeacher: Teacher
        private lateinit var aSubject : Subject
        private lateinit var otherSubject : Subject

    @BeforeEach
        fun setUp(){
            subjectController = SubjectController(subjectService, teacherService)
            aTeacher = Teacher(name = "El que falto", email ="faltante@fgh.fk", password="12345")
            otherTeacher = Teacher(name = "Uno que no falto", email="nofalto@unq", password="sarmiento")
            teacherService.save(aTeacher)
            teacherService.save(otherTeacher)

            aSubject = subjectController.save(SubjectDTO("POO", "SECUNDARY")).body!!
            otherSubject = subjectController.save(SubjectDTO("PCONC", "SECUNDARY")).body!!
        }

        @Test
        @DisplayName("A Subject can be save")
        fun testASubjectCanBeSave(){
            val foundSubject = subjectController.findSubjectById(aSubject.id!!).body
            Assertions.assertEquals(aSubject.name, foundSubject!!.name)
            Assertions.assertEquals(aSubject.cycle, foundSubject.cycle)
        }
    /*
            @Test
            @DisplayName("A lack can´t be save when date is previous to now")
            fun testALackCannotBeSave(){
                val wrongDateLackDTO = LackDTO(123L,"CONTEST", "1987-04-14", "1987-04-14",aTeacher.id!!)
                val response = lackController.save(wrongDateLackDTO)
                Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
            }

            @Test
            @DisplayName("A lack can´t be save when Article is wrong")
            fun testALackCannotBeSaveWhenArticleIsWrong(){
                val wrongDateLackDTO = LackDTO(savedLack.id, "ASD", "2024-04-14", "2024-04-14",aTeacher.id!!)
                val response = lackController.save(wrongDateLackDTO)
                Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
            }

            @Test
            @DisplayName("A lack can´t be save when Date cannot be parse")
            fun testALackCannotBeSaveWhenDateIsWrong(){
                val wrongDateLackDTO = LackDTO(123L,"CONTEST", "fghj", "2024-04-14",aTeacher.id!!)
                val response = lackController.save(wrongDateLackDTO)
                Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
            }

            @Test
            @DisplayName("A lack can´t be save when teacher id is wrong")
            fun testALackCannotBeSaveWhenIDIsWrong(){
                val wrongDateLackDTO = LackDTO(article = "CONTEST", beginDate = "2025-01-01", endDate = "2025-01-01", idTeacher = 420000L)
                val response = lackController.save(wrongDateLackDTO)
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
            }

            @Test
            @DisplayName("LackController returns an empty list when the teacher doesnt faul")
            fun testLackControllerReturnsAnEmptyListWhenTheTeacherDoesntFaul(){
                val response = lackController.lacksOf(otherTeacher.id).body
                Assertions.assertEquals(0, response?.size)
            }

            @Test
            @DisplayName("LackController returns an empty list when the teacher doesnt faul")
            fun testLackControllerReturnsAnListWithOneElementeWhenTheTeacherHasOnlyALack(){
                val response = lackController.lacksOf(aTeacher.id).body
                Assertions.assertEquals(1, response?.size)
            }

            @Test
            @DisplayName("LackController returns an empty list when the teacher doesnt faul")
            fun testLackControllerThrowAnExceptionWhenTheTeacherDoesntExist(){
                val response = lackController.lacksOf(57483920L)
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
            }

            @Test
            @DisplayName("LackController returns NotFOUNDStatus when find by wrong ID")
            fun testLackControllerReturnsNotFOUNDStatusWhenFindByWrongID(){
                val response = lackController.findLackById(57483920L.toString())
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
            }

            @Test
            @DisplayName("LackController returns an OK when delete an Lack by a rigth ID")
            fun testLackControllerReturnsAnOKWhenDeleteAnLackByARigthID(){
                val otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2026-12-30", "2026-12-30", aTeacher.id!!)
                val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
                val response = lackController.deleteLack(otherSavedLack.id)
                Assertions.assertEquals(HttpStatus.OK, response.statusCode)
            }

            @Test
            @DisplayName("LackController delete a lack when delete an Lack by a rigth ID")
            fun testLackControllerCanDeleteALackWhenDeleteAnLackByARigthID(){
                val otherParticularLackDTO = LackDTO(null, "PARTICULAR", "2023-12-30", "2023-12-30", aTeacher.id!!)
                val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
                val antiqueCount = lackRepository.count()
                lackController.deleteLack(otherSavedLack.id)
                Assertions.assertEquals(antiqueCount - 1, lackRepository.count())
            }

            @Test
            @DisplayName("LackController returns an NOTFOUND when delete an Lack by a wrong ID")
            fun testLackControllerReturnsAnNOTFOUNDWhenDeleteAnLackByAWrongID(){
                val response = lackController.deleteLack(123456789012345L)
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
            }

            @Test
            @DisplayName("LackController cant delete a lack when delete an Lack by a wrong ID")
            fun testLackControllerCannotDeleteALackWhenDeleteAnLackByAWrongID(){
                val antiqueCount = lackRepository.count()
                lackController.deleteLack(123456789012345L)
                Assertions.assertEquals(antiqueCount, lackRepository.count())
            }

            @Test
            @DisplayName("LackController can update the article field for a Lack")
            fun testLackControllerReturnsAnOKWhenUpdateTheArticleFieldForALack(){
                val otherParticularLackDTO = LackDTO(null,"PARTICULAR", "2025-12-31", "2025-12-31", aTeacher.id!!)
                val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
                val updateDTO = LackDTO(otherSavedLack.id, "STUDYDAY", "2023-12-31","2023-12-31", aTeacher.id!!)
                val response = lackController.updateLackById(updateDTO)
                Assertions.assertEquals(HttpStatus.OK, response.statusCode)
            }

            @Test
            @DisplayName("LackController can update the article field for a Lack")
            fun testLackControllerCanUpdateTheArticleFieldForALack(){
                val otherParticularLackDTO = LackDTO(null,"PARTICULAR", "2024-12-31", "2024-12-31", aTeacher.id!!)
                val otherSavedLack = lackController.save(otherParticularLackDTO).body!!
                otherParticularLackDTO.id = otherSavedLack.id!!
                val responseBefore = lackController.findLackById(otherSavedLack.id!!.toString())
                Assertions.assertEquals(Article.PARTICULAR, responseBefore.body!!.article)
                val updateDTO = LackDTO(otherSavedLack.id,"STUDYDAY", "2023-12-31", "2023-12-31", aTeacher.id!!)
                lackController.updateLackById(updateDTO)
                val responseAfter = lackController.findLackById(otherSavedLack.id!!.toString())
                Assertions.assertEquals(Article.STUDYDAY, responseAfter.body!!.article)
            }

        }
        */
}