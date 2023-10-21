package ar.edu.unq.tpi.ciriaqui.exception

import ar.edu.unq.tpi.ciriaqui.TeacherNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
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
        val errorResponse = ErrorResponse("No se pudo encontrar la entidad buscada", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(IncorrectDateException::class)
    fun handleIncorrectDateExceptionException(ex: IncorrectDateException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar la entidad buscada", ex.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(LackNotFoundException::class)
    fun handleLackNotFoundException(ex: LackNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar la entidad buscada", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(SubjectFoundException::class)
    fun handleSubjectNotFoundException(ex: SubjectFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar la entidad buscada", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }


    @ExceptionHandler(TeacherNotFoundException::class)
    fun handleTeacherNotFoundException(ex: TeacherNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("No se pudo encontrar la entidad buscada", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("Error interno del servidor", ex.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}