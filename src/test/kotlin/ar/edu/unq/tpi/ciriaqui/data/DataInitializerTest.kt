package ar.edu.unq.tpi.ciriaqui.data

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class DataInitializerTest {
    @Autowired
    private lateinit var teacherRepository : TeacherRepository
    @Autowired
    private lateinit var lackRepository : LackRepository
    private lateinit var lackRepositoryMock : LackRepository
    private lateinit var teacherRepositoryMock : TeacherRepository

    @Test
    @DisplayName("")
    fun testDataInitializerSaveFiveTeachers(){
        assertEquals(5, teacherRepository.count())
    }

    @Test
    @DisplayName("")
    fun testDataInitializerSaveThreeLacks(){
        assertEquals(5, teacherRepository.count())
    }

}