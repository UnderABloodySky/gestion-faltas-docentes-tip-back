package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dto.SearchDTO
import ar.edu.unq.tpi.ciriaqui.exception.DuplicateLackInDateException
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectDateException
import ar.edu.unq.tpi.ciriaqui.exception.LackNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.service.LackService
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeParseException


@RestController
@RequestMapping("/lacks")
class LackController(@Autowired var lackService: LackService, @Autowired var teacherService: TeacherService) {
    @GetMapping("/id/{id}")
    fun findLackById(@PathVariable("id") id: Long): ResponseEntity<Lack> {
        return try{
            val foundLack = lackService.findLackById(id)
            ResponseEntity(foundLack, HttpStatus.OK)
        }
        catch(err : LackNotFoundException){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("")
    fun save(@RequestBody aLackDTO: LackDTO): ResponseEntity<Lack> {
        try {
            val createLack = lackService.save(aLackDTO)
            return ResponseEntity.ok(createLack)
        } catch (errA: TeacherNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (errB: DuplicateLackInDateException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        } catch (errC: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @GetMapping("/id-teacher/{id}")
    fun lacksOf(@PathVariable id: Long,
                @RequestParam(value = "begin-date", required = false) beginDate: String? = null,
                @RequestParam(value = "end-date", required = false) endDate: String? = null
    ): ResponseEntity<List<Lack?>> {
        val teacher = try {
            teacherService.findTeacherById(id)
        }catch(err: TeacherNotFoundException){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity.ok(lackService.lacksOf(SearchDTO(teacherId = teacher?.id, beginDate, endDate)))
    }

    @GetMapping("/id-subject/{id}")
    fun lacksOfTeacherThatInstructs(
        @PathVariable id: Long?,
        @RequestParam(value = "begin-date", required = false) beginDate: String?,
        @RequestParam(value = "end-date", required = false) endDate: String?
    ): ResponseEntity<List<Lack>> {
        val teacher = try {
            teacherService.findTeacherById(id!!)
        } catch (err: TeacherNotFoundException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (errB : DateTimeParseException){
            throw IncorrectDateException()
        }
        return ResponseEntity.ok(lackService.lacksOf(SearchDTO(teacher?.id, beginDate!!, endDate!!)))
    }

    @GetMapping("/name/{partial}")
    fun lacksOfNameThatInstructs(
        @PathVariable partial: String?,
        @RequestParam(value = "begin-date", required = false) beginDate: String?,
        @RequestParam(value = "end-date", required = false) endDate: String?
    ): ResponseEntity<List<Lack?>> {
        val lacksTeachers = try {
            lackService.findByPartialName(SearchDTO(name=partial!!, beginDate = beginDate!!, endDate = endDate!!))
        } catch (err: TeacherNotFoundException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (errB : DateTimeParseException){
            throw IncorrectDateException()
        }
        return ResponseEntity.ok(lacksTeachers)
    }

    @GetMapping("/articles")
    fun typesOfArticle() : ResponseEntity<Array<Article>> = ResponseEntity.ok(Article.values())

    @DeleteMapping("/id/{id}")
    fun deleteLack(@PathVariable id : Long?) :ResponseEntity<HttpStatus>{
        try{
            this.lackService.deleteLackById(id)
        }
        catch(err:Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("")
    fun updateLackById(@RequestBody updateDTO: LackDTO): ResponseEntity<Lack> {
        val lackToUpdate = try{
            this.lackService.updatelackById(updateDTO)
        }
        catch(err:LackNotFoundException){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        catch(err:IncorrectDateException){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(lackToUpdate, HttpStatus.OK)
    }
}
