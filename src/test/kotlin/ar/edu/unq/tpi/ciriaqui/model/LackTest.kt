package ar.edu.unq.tpi.ciriaqui.model

import ar.edu.unq.tpi.ciriaqui.dto.LoginDTO
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectDateException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@SpringBootTest
@ExtendWith(SpringExtension::class)
class LackTest {
    private lateinit var aLack : Lack
    private lateinit var aDate : LocalDate
    private lateinit var otherDate : LocalDate
    private lateinit var aTeacher : Teacher

    @BeforeEach
    fun setUp() {
        aDate = LocalDate.now()
        otherDate = LocalDate.now()
        aTeacher = Teacher()
        aLack = Lack(Article.STUDYDAY, aDate, otherDate, aTeacher)
    }

    @Test
    @DisplayName("A lack has an article")
    fun testALackHasAnArticle(){
        assertEquals(Article.STUDYDAY, aLack.article)
    }

    @Test
    @DisplayName("A lack has an article")
    fun testALackHasAnDate(){
        assertEquals(aDate, aLack.beginDate)
    }

    @Test
    @DisplayName("A lack has not an ID")
    fun testALackHasAnID(){
        assertNull(aLack.id)
    }

    @Test
    @DisplayName("A lack has an end date")
    fun testALackHasAnEndDate(){
        assertEquals(aDate, aLack.endDate)
    }


    @Test
    @DisplayName("A lack Cannot be created in an earlier begindDate")
    fun testALackCannotBeCreatedInAnEarlierBegindDate(){
        assertThrows<IncorrectDateException> {
            val beginDate = LocalDate.of(2024, 1, 1)
            val endDate = LocalDate.of(2023, 12, 31)
            val aTeacher = Teacher()
            Lack(Article.STUDYDAY, beginDate, endDate, aTeacher)
        }
    }

    //@Test
    @DisplayName("A lack Cannot be created in a weekend day")
    fun testALackCannotBeCreatedInAWeekendDayForBeginDate(){
        assertThrows<IncorrectDateException> {
            val beginDate = LocalDate.of(2023, 9, 2)
            val endDate = LocalDate.of(2023, 12, 31)
            val aTeacher = Teacher()
            Lack(Article.STUDYDAY, beginDate, endDate, aTeacher)
        }
    }

}