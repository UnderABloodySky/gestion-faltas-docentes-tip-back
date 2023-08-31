package ar.edu.unq.tpi.ciriaqui.controller
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teachers")
class TeacherController(@Autowired private val teacherService: TeacherService) {


    @GetMapping("/")
    fun getHello(): ResponseEntity<String> {
        return ResponseEntity.ok("/teacher")
    }

    @GetMapping("/{email}")
    fun getTeacherByEmail(@PathVariable email: String): ResponseEntity<Teacher> {
        val teacher: Teacher?
        try{
            teacher = teacherService.findTeacherByEmail(email)
        }
        catch(err : TeacherNotFoundException){
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(teacher)
    }
}
