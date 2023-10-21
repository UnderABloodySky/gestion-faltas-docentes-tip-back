package ar.edu.unq.tpi.ciriaqui.dao

import ar.edu.unq.tpi.ciriaqui.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface StudentRepository : JpaRepository<Student, Long> {

    fun findById(id: Long?): Optional<Student>

    @Query("SELECT s FROM Student s " +
            "WHERE s.record = :rec ")
    fun findByRecord(rec: Int): Optional<Student>


}
