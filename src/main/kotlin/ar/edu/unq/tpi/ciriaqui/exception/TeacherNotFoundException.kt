package ar.edu.unq.tpi.ciriaqui

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class TeacherNotFoundException(id: String) : RuntimeException("Teacher with $id doesn't exist")