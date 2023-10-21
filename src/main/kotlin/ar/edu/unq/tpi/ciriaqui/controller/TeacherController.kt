package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dto.LoginDTO
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teachers")
class TeacherController(@Autowired private val teacherService: TeacherService) {

    @GetMapping("/")
    fun sayHello() : ResponseEntity<String> = ResponseEntity("Hi, I´m ciriaqui", HttpStatus.OK)

    @GetMapping("/email/{email}")
    fun getTeacherByEmail(@PathVariable("email") email: String): ResponseEntity<Teacher> {
        val teacher = teacherService.findTeacherByEmail(email)
        return ResponseEntity(teacher, HttpStatus.OK)
    }

    @GetMapping("/id/{id}")
    fun getTeacherByID(@PathVariable("id") id: Long): ResponseEntity<Teacher> {
        val teacher = teacherService.findTeacherById(id)
        return ResponseEntity(teacher, HttpStatus.OK)
    }

    @GetMapping("/name/{partial}")
    fun getTeachersWithPartialName(@PathVariable("partial") partial : String) : ResponseEntity<List<Teacher>> = ResponseEntity.ok(teacherService.findByPartialName(partial))

    @PostMapping("/login")
    fun login(@RequestBody credentials : LoginDTO): ResponseEntity<Teacher> {
        val teacher = teacherService.login(credentials)
        return ResponseEntity(teacher, HttpStatus.OK)
    }
}
