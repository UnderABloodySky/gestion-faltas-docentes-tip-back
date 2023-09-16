package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectDateException
import ar.edu.unq.tpi.ciriaqui.exception.LackNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import jakarta.persistence.Entity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.AbstractPersistable_
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*


@Service
class LackService(@Autowired var lackRepository : LackRepository) {
    fun save(article: Article, beginDate: LocalDate, endDate: LocalDate = beginDate, teacher: Teacher) : Lack{
        return if(this.isCorrectDate(beginDate)) lackRepository.save(Lack(article, beginDate, endDate, teacher)) else throw IncorrectDateException()
    }

    fun findLackById(id: Long?): Lack? {
        val foundLack = lackRepository.findById(id!!)
        return if(foundLack.isPresent) foundLack.get() else throw LackNotFoundException(id)
    }

    private fun isCorrectDate(date: LocalDate) : Boolean = LocalDate.now() <= date
    fun lacksOf(id: Long?): List<Lack> = lackRepository.findAllByTeacherId(id!!)

    fun deleteLackById(id: Long?){
        val lackOptional: Optional<Lack> = lackRepository.findById(id)
        if(lackOptional.isPresent()){
            lackRepository.deleteById(id!!)
        }
        else{
            throw LackNotFoundException(id)
        }

    }

}
