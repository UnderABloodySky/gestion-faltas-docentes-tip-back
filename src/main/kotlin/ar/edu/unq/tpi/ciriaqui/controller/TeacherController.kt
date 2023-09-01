package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.DTO.LoginDTO
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectCredentialException
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
    fun getHello(): ResponseEntity<String> {
        return ResponseEntity.ok("/teacher")
    }

    @GetMapping("/email/{email}")
    fun getTeacherByEmail(@PathVariable("email") email: String): ResponseEntity<Teacher> {
        val teacher: Teacher?
        return try{
            teacher = teacherService.findTeacherByEmail(email)
            ResponseEntity(teacher, HttpStatus.OK)
        }
        catch(err : TeacherNotFoundException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/id/{id}")
    fun getTeacherByID(@PathVariable("id") id: String): ResponseEntity<Teacher> {
        val teacher: Teacher?
        return try{
            teacher = teacherService.findTeacherById(id as Long)
            ResponseEntity(teacher, HttpStatus.OK)
        }
        catch(err : TeacherNotFoundException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody credentials : LoginDTO): ResponseEntity<Teacher> {
        val teacher: Teacher?
        return try {
            teacher = teacherService.login(credentials)
            ResponseEntity(teacher, HttpStatus.OK)
        }catch (err: IncorrectCredentialException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
