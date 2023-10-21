package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dto.StudentDTO
import ar.edu.unq.tpi.ciriaqui.model.Course
import ar.edu.unq.tpi.ciriaqui.model.Student
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import ar.edu.unq.tpi.ciriaqui.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
class StudentController(@Autowired private val studentService : StudentService) {
    @GetMapping("/id/{id}")
    fun getStudentByID(@PathVariable("id") id: Long): ResponseEntity<Student> {
        val student = studentService.findStudentById(id)
        return ResponseEntity(student, HttpStatus.OK)
    }

    @GetMapping("/record/{rec}")
    fun getStudentByRecord(@PathVariable("rec") rec: Int): ResponseEntity<Student> {
        val student = studentService.findStudentByRecord(rec)
        return ResponseEntity(student, HttpStatus.OK)
    }


    @PostMapping("")
    fun save(aStudentDTO: StudentDTO): ResponseEntity<Student> {
        val student = studentService.saveStudent(aStudentDTO)
        return ResponseEntity.ok(student)
    }
}