package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectDateException
import ar.edu.unq.tpi.ciriaqui.exception.LackNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LackService(@Autowired var lackRepository : LackRepository) {
    fun save(article: Article, beginDate: LocalDate, teacher: Teacher) : Lack{
        return if(this.isCorrectDate(beginDate)) lackRepository.save(Lack(article, beginDate, teacher)) else throw IncorrectDateException()
    }

    fun findLackById(id: Long?): Lack? {
        val foundLack = lackRepository.findById(id!!)
        return if(foundLack.isPresent) foundLack.get() else throw LackNotFoundException(id)
    }

    private fun isCorrectDate(date: LocalDate) : Boolean = LocalDate.now() <= date

}
