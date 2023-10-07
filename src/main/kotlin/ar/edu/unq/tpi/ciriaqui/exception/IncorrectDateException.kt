package ar.edu.unq.tpi.ciriaqui.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class IncorrectDateException() : RuntimeException("The creation date of a Lack cannot be earlier than the current day")
