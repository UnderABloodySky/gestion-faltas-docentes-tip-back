package ar.edu.unq.tpi.ciriaqui.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
class TeacherMustInstructSubjectException : RuntimeException("At least one teacher must teach the subject")
