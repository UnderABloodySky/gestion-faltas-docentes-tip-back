package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import ar.edu.unq.tpi.ciriaqui.dto.SubjectDTO
import ar.edu.unq.tpi.ciriaqui.exception.BadNameException
import ar.edu.unq.tpi.ciriaqui.exception.DuplicateLackInDateException
import ar.edu.unq.tpi.ciriaqui.exception.LackNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Cycle
import ar.edu.unq.tpi.ciriaqui.model.Subject
import ar.edu.unq.tpi.ciriaqui.service.SubjectService
import ar.edu.unq.tpi.ciriaqui.service.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subjects")
class SubjectController(@Autowired var subjectService: SubjectService, @Autowired var teacherService: TeacherService) {

    @PostMapping("")
    fun save(aSubjectDTO : SubjectDTO): ResponseEntity<Subject> {
        return try {
            ResponseEntity.ok(subjectService.save(aSubjectDTO))
        }catch(err : BadNameException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    fun findSubjectById(aID: String): ResponseEntity<Subject?> {
        val idToLong = try{
            java.lang.Long.parseLong(aID)
        }catch(err: Exception){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return try{
            val foundLack = subjectService.findSubjectById(idToLong)
            ResponseEntity(foundLack, HttpStatus.OK)
        }
        catch(err : LackNotFoundException){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}
