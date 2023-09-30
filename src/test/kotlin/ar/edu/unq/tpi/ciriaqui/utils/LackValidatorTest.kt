package ar.edu.unq.tpi.ciriaqui.utils

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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
class LackValidatorTest {
    @Autowired
    private lateinit var lackRepository : LackRepository
    lateinit var lackValidator: LackValidator

    @BeforeEach
    fun setUp() {
        lackValidator = LackValidator(lackRepository)
    }

    @DisplayName("LackValidator returns True when valid a lack from a teacher without lacks")
    @Test
    fun lackValidatorReturnTrueWhenValidALackFromATeacherWithoutLacks(){
        val minervaLackDTO = LackDTO(null, "STUDYDAY", "2024-04-14", "2024-04-14", 2L)
        assertTrue(lackValidator.isValid(minervaLackDTO))
    }

    @DisplayName("LackValidator returns False when valid a lack from a teacher with a lack in exactly this range of dates")
    @Test
    fun lackValidatorReturnTrueWhenValidALackFromATeacherWithALackInExactlyThisRangeOfDates(){
        val minervaLackDTO = LackDTO(null, "STUDYDAY", "2023-12-30" , "2023-12-30", 5L)
        assertFalse(lackValidator.isValid(minervaLackDTO))
    }

    @DisplayName("LackValidator returns False when valid a lack from a teacher with a lack into a range that includes a begin date exactly")
    @Test
    fun lackValidatorReturnTrueWhenValidALackFromATeacherWithALackInExactlyThisRangeOfDatesIncludeBeginDate(){
        val minervaLackDTO = LackDTO(null, "STUDYDAY", "2023-11-15" , "2023-12-01", 3L)
        assertFalse(lackValidator.isValid(minervaLackDTO))
    }

    @DisplayName("LackValidator returns False when valid a lack from a teacher with a lack into a range that includes a end date exactly")
    @Test
    fun lackValidatorReturnTrueWhenValidALackFromATeacherWithALackInExactlyThisRangeOfDatesIncludeEndDate(){
        val minervaLackDTO = LackDTO(null, "STUDYDAY", "2023-12-31" , "2024-01-02", 3L)
        assertFalse(lackValidator.isValid(minervaLackDTO))
    }

    @DisplayName("LackValidator returns False when valid a lack from a teacher with a lack into a range that includes a end date exactly")
    @Test
    fun lackValidatorReturnTrueWhenValidALackFromATeacherWithALackInExactlyrangeBetweenBeginDateAndEndDate(){
        val minervaLackDTO = LackDTO(null, "STUDYDAY", "2023-12-05" , "2024-01-09", 3L)
        assertFalse(lackValidator.isValid(minervaLackDTO))
    }

    @DisplayName("LackValidator returns False when valid a lack from a teacher with a lack into a range that includes a end date exactly")
    @Test
    fun lackValidatorReturnTrueWhenValidALackFromATeacherWithALackInExactlyBetweenBeginDateAndEndDate(){
        val minervaLackDTO = LackDTO(null, "STUDYDAY", "2023-11-01" , "2024-01-09", 3L)
        assertFalse(lackValidator.isValid(minervaLackDTO))
    }
}
