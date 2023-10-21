package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dto.LoginDTO
import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import ar.edu.unq.tpi.ciriaqui.exception.*
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class TeacherService(@Autowired private val teacherRepository: TeacherRepository) {
    fun findTeacherById(aTeacherID: Long): Teacher {
        return teacherRepository.findById(aTeacherID)
            .orElseThrow { TeacherNotFoundException(aTeacherID.toString()) }
    }

    fun save(aTeacher: Teacher): Teacher {
        return teacherRepository.save(aTeacher)
    }

    fun findTeacherByEmail(anEmail: String): Teacher {
        return teacherRepository.findTeacherByEmail(anEmail)
            .orElseThrow { TeacherNotFoundException("No se encontró al profesor con email: $anEmail") }
    }

    fun login(aLoginDTO: LoginDTO): Teacher {
        val teacher = findTeacherByEmail(aLoginDTO.email)
        if (!teacher.isCorrectPassword(aLoginDTO.password)) {
            throw IncorrectCredentialException()
        }
        return teacher
    }

    fun findByPartialName(partial: String?): List<Teacher> {
        return teacherRepository.findAllWithPartialName(partial)
    }
}
