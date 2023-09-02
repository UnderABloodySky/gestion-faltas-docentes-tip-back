package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.dto.LoginDTO
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectCredentialException
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeacherService(@Autowired var teacherRepository: TeacherRepository) {
    fun findTeacherById(aTeacherID: Long) : Teacher? {
        val optionalTeacher = teacherRepository.findById(aTeacherID)
        return this.returnTeacherIfExiste(aTeacherID, optionalTeacher)
    }
    fun save(aTeacher: Teacher) {
        try{
            teacherRepository.save(aTeacher)
        }
        catch(err : Exception){
            throw err
        }
    }

    fun findTeacherByEmail(anEmail: String): Teacher? {
        val optionalTeacher = teacherRepository.findTeacherByEmail(anEmail)
        return this.returnTeacherIfExiste(anEmail, optionalTeacher)
    }

    fun login(aLoginDTO: LoginDTO) : Teacher?{
        val teacher = try{
            this.findTeacherByEmail(aLoginDTO.email)
        }catch(err : TeacherNotFoundException){
            throw IncorrectCredentialException()
        }
        return if(teacher!!.isCorrectPassword(aLoginDTO.password)) teacher else throw IncorrectCredentialException()
    }

    private fun returnTeacherIfExiste(anIdentifier : Any, anOptionalTeacher : Optional<Teacher>) : Teacher{
        return if (anOptionalTeacher.isPresent) anOptionalTeacher.get() else throw TeacherNotFoundException(anIdentifier.toString())
    }
}
