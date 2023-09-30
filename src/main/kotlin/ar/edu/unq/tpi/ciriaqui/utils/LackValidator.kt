package ar.edu.unq.tpi.ciriaqui.utils

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

class LackValidator(@Autowired var lackRepository : LackRepository){

    fun isValid(aLackDTO: LackDTO): Boolean {
        val beginDate = LocalDate.parse(aLackDTO.beginDate)
        val endDate = LocalDate.parse(aLackDTO.endDate)
        return lackRepository.countLacksInDateRange(aLackDTO.idTeacher, beginDate, endDate) == 0L
    }
}