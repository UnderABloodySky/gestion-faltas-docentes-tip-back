package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
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


@RestController
@RequestMapping("/lacks")
class LackController(@Autowired var lackService: LackService, @Autowired var teacherService: TeacherService) {
    @GetMapping("/id/{id}")
    fun findLackById(@PathVariable("id") id: String): ResponseEntity<Lack> {
        val idToLong = try{
            java.lang.Long.parseLong(id)
        }catch(err: Exception){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return try{
            val foundLack = lackService.findLackById(idToLong)
            ResponseEntity(foundLack, HttpStatus.OK)
        }
        catch(err : LackNotFoundException){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("")
    fun save(@RequestBody aLackDTO : LackDTO) : ResponseEntity<Lack> {
        return try{
            val createLack = lackService.save(aLackDTO)
            ResponseEntity(createLack, HttpStatus.OK)
        }catch(err: TeacherNotFoundException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }catch(errC: Exception){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/id-teacher/{id}")
    fun lacksOf(@PathVariable id: Long?): ResponseEntity<List<Lack>> {
        val teacher = try {
            teacherService.findTeacherById(id!!)
        }catch(err: TeacherNotFoundException){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(lackService.lacksOf(teacher?.id), HttpStatus.OK)
    }

    @GetMapping("/articles")
    fun typesOfArticle() : ResponseEntity<Array<Article>> = ResponseEntity(Article.values(), HttpStatus.OK)

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
