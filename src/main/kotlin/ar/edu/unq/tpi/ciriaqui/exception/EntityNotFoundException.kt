package ar.edu.unq.tpi.ciriaqui.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.NOT_FOUND)
open class EntityNotFoundException(anEntity : String, id: Any?) : RuntimeException("$anEntity with ID $id not found")