package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectCredentialException
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectDateException
import ar.edu.unq.tpi.ciriaqui.exception.LackNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class LackService(@Autowired var teacherService : TeacherService, @Autowired var lackRepository : LackRepository) {
    fun save(aLackDTO : LackDTO) : Lack{
        val article = Article.valueOf(aLackDTO.article)
        val beginDate = LocalDate.parse(aLackDTO.beginDate, DateTimeFormatter.ISO_LOCAL_DATE)
        val endDate = LocalDate.parse(aLackDTO.endDate, DateTimeFormatter.ISO_LOCAL_DATE)
        val teacher = teacherService.findTeacherById(aLackDTO.idTeacher)
        return if(this.isCorrectDate(beginDate) && this.isCorrectDate(endDate)) lackRepository.save(Lack(article, beginDate, endDate, teacher)) else throw IncorrectDateException()
    }

    fun findLackById(id: Long?): Lack? {
        val foundLack = lackRepository.findById(id!!)
        return if(foundLack.isPresent) foundLack.get() else throw LackNotFoundException(id)
    }

    private fun isCorrectDate(date: LocalDate) : Boolean = LocalDate.now() <= date
    fun lacksOf(id: Long?): List<Lack> = lackRepository.findAllByTeacherId(id!!)

    fun deleteLackById(id: Long?){
        val lackOptional: Optional<Lack> = lackRepository.findById(id)
        if(lackOptional.isPresent){
            lackRepository.deleteById(id!!)
        }
        else{
            throw LackNotFoundException(id)
        }
    }

    fun updatelackById(updateDTO: LackDTO): Lack? {
        val id = updateDTO.id
        val lackTpUpdate = try{
            this.findLackById(id)
        }catch(err: Exception){
            throw LackNotFoundException(id)
        }
        if(updateDTO.idTeacher != lackTpUpdate?.teacher!!.id){
            throw IncorrectCredentialException()
        }
        val beginDate = LocalDate.parse(updateDTO.beginDate, DateTimeFormatter.ISO_LOCAL_DATE)
        val endDate = LocalDate.parse(updateDTO.beginDate, DateTimeFormatter.ISO_LOCAL_DATE)
        if(beginDate > endDate || beginDate < LocalDate.now() || endDate < LocalDate.now()){
            throw IncorrectDateException()
        }
        lackTpUpdate.article = Article.valueOf(updateDTO.article)
        lackTpUpdate.beginDate = beginDate
        lackTpUpdate.endDate = endDate
        return lackRepository.save(lackTpUpdate)
    }

}
