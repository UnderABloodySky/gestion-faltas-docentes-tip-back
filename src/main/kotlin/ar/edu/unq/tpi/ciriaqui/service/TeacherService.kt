package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeacherService(var teacherRepository: TeacherRepository) {
    fun findTeacherById(aTeacherID: Long): Teacher? {
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

    private fun returnTeacherIfExiste(anIdentifier : Any, anOptionalTeacher : Optional<Teacher>) : Teacher?{
        if (anOptionalTeacher.isPresent) {
            return anOptionalTeacher.get()
        } else {
            throw TeacherNotFoundException(anIdentifier.toString())
        }
    }
}
