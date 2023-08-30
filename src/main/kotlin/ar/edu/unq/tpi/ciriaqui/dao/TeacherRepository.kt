package ar.edu.unq.tpi.ciriaqui.dao

import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TeacherRepository : JpaRepository<Teacher, Long> {
    fun findTeacherByEmail(email: String): Optional<Teacher>
}