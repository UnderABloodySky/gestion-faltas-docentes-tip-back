package ar.edu.unq.tpi.ciriaqui.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class SubjectFoundException(id: Long) : RuntimeException("Subject with ${id} doesn't exist") {

}
