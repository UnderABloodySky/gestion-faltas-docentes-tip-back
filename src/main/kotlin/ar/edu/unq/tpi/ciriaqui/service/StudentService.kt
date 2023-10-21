package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.dao.StudentRepository
import ar.edu.unq.tpi.ciriaqui.dto.StudentDTO
import ar.edu.unq.tpi.ciriaqui.exception.StudentNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Course
import ar.edu.unq.tpi.ciriaqui.model.Student
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class StudentService(@Autowired val studentRepository : StudentRepository) {
    fun findStudentById(id: Long): Student? {
        val optionalStudent = studentRepository.findById(id)
        return this.returnTeacherIfExiste(id, optionalStudent)
    }

    fun findStudentByRecord(rec: Int): Student? {
        val optionalStudent = studentRepository.findByRecord(rec)
        return this.returnTeacherIfExiste(rec, optionalStudent)
    }

    fun saveStudent(aStudentDTO: StudentDTO): Student? {
        val course = Course.valueOf(aStudentDTO.course)
        val student = Student(aStudentDTO.name, aStudentDTO.record, course)
        return studentRepository.save(student)
    }

    private fun returnTeacherIfExiste(anIdentifier : Any, anOptionalStudent : Optional<Student>) : Student {
        return if (anOptionalStudent.isPresent) anOptionalStudent.get() else throw StudentNotFoundException(anIdentifier.toString())
    }

}
