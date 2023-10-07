package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dto.SearchDTO
import ar.edu.unq.tpi.ciriaqui.exception.DuplicateLackInDateException
import ar.edu.unq.tpi.ciriaqui.exception.IncorrectDateException
import ar.edu.unq.tpi.ciriaqui.exception.LackNotFoundException
import ar.edu.unq.tpi.ciriaqui.exception.SubjectFoundException
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.service.InstructService
import ar.edu.unq.tpi.ciriaqui.service.LackService
import ar.edu.unq.tpi.ciriaqui.service.SubjectService
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeParseException
import java.util.stream.Stream


@RestController
@RequestMapping("/lacks")
class LackController(
    @Autowired var lackService: LackService,
    @Autowired var teacherService: TeacherService,
    @Autowired var subjectService: SubjectService,
    @Autowired var instructService: InstructService
) {
    @GetMapping("/id/{id}")
    fun findLackById(@PathVariable("id") id: Long): ResponseEntity<Lack> {
        return try{
            val foundLack = lackService.findLackById(id)
            ResponseEntity.ok(foundLack)
        }
        catch(err : LackNotFoundException){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("")
    fun save(@RequestBody aLackDTO: LackDTO): ResponseEntity<Lack> {
        return try {
            val createLack = lackService.save(aLackDTO)
            ResponseEntity.ok(createLack)
        } catch (errA: TeacherNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (errB: DuplicateLackInDateException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        } catch (errC: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
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
        return ResponseEntity.ok(lackService.lacksOf(SearchDTO(teacherId = teacher?.id, beginDate = beginDate, endDate = endDate)))
    }

    @GetMapping("/id-subject/{id}")
    fun lacksOfTeacherThatInstructs(
        @PathVariable id: Long?,
        @RequestParam(value = "begin-date", required = false) beginDate: String?,
        @RequestParam(value = "end-date", required = false) endDate: String?
    ): ResponseEntity<List<Lack>> {
        val subject = try {
            subjectService.findSubjectById(id!!)
        } catch (err: SubjectFoundException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (errB : DateTimeParseException){
            throw IncorrectDateException()
        }
        val teachers = try{
                instructService.findTeachersBySubjectId(subject?.id!!)
        }catch(err : Exception){
            throw err
        }
        val finalLacks = teachers.stream().flatMap { teacher ->
            val lacks = lackService.lacksOf(SearchDTO(teacherId = teacher, beginDate = beginDate, endDate = endDate))
            Stream.of(lacks)
        }
        return ResponseEntity.ok(finalLacks as List<Lack>)
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
        catch(err:DuplicateLackInDateException){
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        return ResponseEntity(lackToUpdate, HttpStatus.OK)
    }
}
