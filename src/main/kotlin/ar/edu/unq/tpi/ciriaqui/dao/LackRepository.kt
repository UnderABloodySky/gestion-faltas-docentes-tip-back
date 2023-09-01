package ar.edu.unq.tpi.ciriaqui.dao

import ar.edu.unq.tpi.ciriaqui.model.Lack
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LackRepository  : JpaRepository<Lack, Long>{
    @EntityGraph(attributePaths = ["teacher"])
    fun findById(id: Long?): Optional<Lack>
    fun findAllByTeacherId(teacherId: Long): List<Lack>
}