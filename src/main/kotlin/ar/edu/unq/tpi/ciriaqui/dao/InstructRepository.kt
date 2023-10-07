package ar.edu.unq.tpi.ciriaqui.dao


import ar.edu.unq.tpi.ciriaqui.model.Instruct
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InstructRepository : JpaRepository<Instruct, Long>{
    @EntityGraph(attributePaths = ["teacher", "subject"])
    fun findById(id: Long?): Optional<Instruct>

    fun findBySubjectId(subjectId: Long): List<Instruct>
}
