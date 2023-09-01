package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectDateException
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.service.LackService
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/lacks")
class LackController(@Autowired var lackService: LackService, @Autowired var teacherService: TeacherService) {
    @GetMapping("/id/{id}")
    fun findLackById(@PathVariable("id") id: Long): ResponseEntity<Lack> {
        val foundLack : Lack?
        return try{
            foundLack = lackService.findLackById(id)
            ResponseEntity(foundLack, HttpStatus.OK)
        }
        catch(err : Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/")
    fun save(@RequestBody aLackDTO : LackDTO) : ResponseEntity<Lack> {
        val article = try {
            Article.valueOf(aLackDTO.article) // Intenta convertir el String en un Article
        }catch (errA: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        val beginDate = try{
            LocalDate.parse(aLackDTO.beginDate, DateTimeFormatter.ISO_LOCAL_DATE)
        }catch(errB: Exception){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        val teacher = try{
            teacherService.findTeacherById(aLackDTO.idTeacher)
        }catch(errC: TeacherNotFoundException){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        val createLack : Lack?
        return try{
            createLack = lackService.save(article, beginDate, teacher!!)
            ResponseEntity(createLack, HttpStatus.OK)
        }catch(errC: IncorrectDateException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


}
