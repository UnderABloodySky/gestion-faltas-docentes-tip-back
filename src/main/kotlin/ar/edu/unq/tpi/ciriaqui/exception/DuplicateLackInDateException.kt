package ar.edu.unq.tpi.ciriaqui.exception

import ar.edu.unq.tpi.ciriaqui.dto.LackDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


//@ResponseStatus(value = HttpStatus.CONFLICT)
class DuplicateLackInDateException(aLackDTO : LackDTO) : RuntimeException("The teacher with ID: ${aLackDTO.idTeacher} already have a lack between ${aLackDTO.beginDate} and ${aLackDTO.endDate}")
