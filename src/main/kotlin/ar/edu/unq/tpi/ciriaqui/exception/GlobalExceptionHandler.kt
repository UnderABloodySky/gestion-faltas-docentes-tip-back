package ar.edu.unq.tpi.ciriaqui.exception

import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler {

    @ExceptionHandler(BadNameException::class)
    fun handleBadNameException(ex: BadNameException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Error de creacion", ex.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(IncorrectCredentialException::class)
    fun handleIncorrectCredentialException(ex: IncorrectCredentialException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Error de creacion", ex.message)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(DuplicateLackInDateException::class)
    fun handleDuplicateLackInDateException(ex: DuplicateLackInDateException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Error de creacion/actualizacion de falta", ex.message)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar la entidad buscada", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(InstructNotFoundException::class)
    fun handleInstructNotFoundException(ex: InstructNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("El profesor no imparte esa materia", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(IncorrectDateException::class)
    fun handleIncorrectDateExceptionException(ex: IncorrectDateException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Fecha incorrecta", ex.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(LackNotFoundException::class)
    fun handleLackNotFoundException(ex: LackNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar la falta buscada", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(SubjectFoundException::class)
    fun handleSubjectNotFoundException(ex: SubjectFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar a la materia buscada", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }


    @ExceptionHandler(TeacherNotFoundException::class)
    fun handleTeacherNotFoundException(ex: TeacherNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar al profesor buscado", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(StudentNotFoundException::class)
    fun handleStudentNotFoundException(ex: StudentNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar al estudiante buscado", ex.message)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }


    @ExceptionHandler(TeacherMustInstructSubjectException::class)
    fun handleTeacherMustInstructSubjectException(ex : TeacherMustInstructSubjectException) : ResponseEntity<ErrorResponse>{
        val errorResponse = ErrorResponse("Argumento equivocado", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex : IllegalArgumentException) : ResponseEntity<ErrorResponse>{
        val errorResponse = ErrorResponse("Argumento equivocado", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Error interno del servidor", ex.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}