package ar.edu.unq.tpi.ciriaqui.dao

import ar.edu.unq.tpi.ciriaqui.model.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s WHERE UPPER(s.name) LIKE UPPER(CONCAT('%', :partial, '%'))")
    fun findAllWithPartialName(partial: String?): List<Subject>
}
