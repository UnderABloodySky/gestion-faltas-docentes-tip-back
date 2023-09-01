package ar.edu.unq.tpi.ciriaqui.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@SpringBootTest
@ExtendWith(SpringExtension::class)
class LackTest {
    private lateinit var aLack : Lack
    private lateinit var aDate : LocalDate
    private lateinit var aTeacher : Teacher

    @BeforeEach
    fun setUp() {
        aDate = LocalDate.now()
        aTeacher = Teacher()
        aLack = Lack(Article.STUDYDAY, aDate, aTeacher)
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

}