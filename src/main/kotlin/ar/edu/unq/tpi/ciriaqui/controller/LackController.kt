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


@RestController
@RequestMapping("/lacks")
class LackController(
    @Autowired var lackService: LackService,
    @Autowired var teacherService: TeacherService,
    @Autowired var subjectService: SubjectService,
    @Autowired var instructService: InstructService) {
    @GetMapping("/id/{id}")
    fun findLackById(@PathVariable("id") id: Long): ResponseEntity<Lack> {
        val foundLack = lackService.findLackById(id)
        return ResponseEntity.ok(foundLack)
    }

    @PostMapping("")
    fun save(@RequestBody aLackDTO: LackDTO): ResponseEntity<Lack> {
            val createLack = lackService.save(aLackDTO)
            return ResponseEntity.ok(createLack)
    }

    @GetMapping("/id-teacher/{id}")
    fun lacksOf(@PathVariable id: Long,
                @RequestParam(value = "begin-date", required = false) beginDate: String? = null,
                @RequestParam(value = "end-date", required = false) endDate: String? = null
    ): ResponseEntity<List<Lack?>> {
        val teacher = teacherService.findTeacherById(id)
        return ResponseEntity.ok(lackService.lacksOf(SearchDTO(teacherId = teacher?.id, beginDate = beginDate, endDate = endDate)))
    }

    @GetMapping("/id-subject/{id}")
    fun lacksOfTeacherThatInstructs(
        @PathVariable id: Long?,
        @RequestParam(value = "begin-date", required = false) beginDate: String?,
        @RequestParam(value = "end-date", required = false) endDate: String?
    ): ResponseEntity<List<Lack>> {
        val subject = subjectService.findSubjectById(id!!)
        val teachers = instructService.findTeachersBySubjectId(subject?.id!!)
        val finalLacks = teachers.stream().map { teacher ->
            val lacks = lackService.lacksOf(SearchDTO(teacherId = teacher, beginDate = beginDate, endDate = endDate))
            lacks
        }
        return ResponseEntity.ok(finalLacks as List<Lack>)
    }

    @GetMapping("/name/{partial}")
    fun lacksOfNameThatInstructs(
        @PathVariable partial: String?,
        @RequestParam(value = "begin-date", required = false) beginDate: String?,
        @RequestParam(value = "end-date", required = false) endDate: String?
    ): ResponseEntity<List<Lack?>> {
        val lacksTeachers = lackService.findByPartialName(SearchDTO(name=partial!!, beginDate = beginDate!!, endDate = endDate!!))
        return ResponseEntity.ok(lacksTeachers)
    }

    @GetMapping("/articles")
    fun typesOfArticle() : ResponseEntity<Array<Article>> = ResponseEntity.ok(Article.values())

    @DeleteMapping("/id/{id}")
    fun deleteLack(@PathVariable id : Long?) :ResponseEntity<HttpStatus>{
            this.lackService.deleteLackById(id)
            return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("")
    fun updateLackById(@RequestBody updateDTO: LackDTO): ResponseEntity<Lack> {
        val lackToUpdate = this.lackService.updatelackById(updateDTO)
        return ResponseEntity(lackToUpdate, HttpStatus.OK)
    }
}
