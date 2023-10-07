package ar.edu.unq.tpi.ciriaqui

import ar.edu.unq.tpi.ciriaqui.exception.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class TeacherNotFoundException(anIncorrectTeacherID: String) : EntityNotFoundException("Teacher", anIncorrectTeacherID)