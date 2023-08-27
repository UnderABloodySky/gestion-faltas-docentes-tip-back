package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.springframework.stereotype.Service

@Service
class TeacherService(var teacherRepository: TeacherRepository) {
    fun findTeacherById(aTeacherID: Long): Teacher {
        val optionalTeacher = teacherRepository.findById(aTeacherID)
        if (optionalTeacher.isPresent) {
            return optionalTeacher.get()
        } else {
            throw TeacherNotFoundException(aTeacherID)
        }
    }
    fun save(aTeacher: Teacher) {
        try{
            teacherRepository.save(aTeacher)
        }
        catch(err : Exception){
            throw err
        }

    }
}
