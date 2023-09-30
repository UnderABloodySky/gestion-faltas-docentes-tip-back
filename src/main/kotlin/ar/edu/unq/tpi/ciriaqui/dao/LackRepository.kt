package ar.edu.unq.tpi.ciriaqui.dao

import ar.edu.unq.tpi.ciriaqui.model.Lack
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface LackRepository  : JpaRepository<Lack, Long>{
    @EntityGraph(attributePaths = ["teacher"])
    fun findById(id: Long?): Optional<Lack>
    fun findAllByTeacherId(teacherId: Long): List<Lack>

    @Query("SELECT COUNT(l) FROM Lack l " +
            "WHERE l.teacher.id = :teacherId " +
            "AND ((l.beginDate BETWEEN :startDate AND :endDate) OR " +
            "     (l.endDate BETWEEN :startDate AND :endDate) OR " +
            "     (:startDate BETWEEN l.beginDate AND l.endDate) OR " +
            "     (:endDate BETWEEN l.beginDate AND l.endDate))")
    fun countLacksInDateRange(
        @Param("teacherId") teacherId: Long,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): Long
}



