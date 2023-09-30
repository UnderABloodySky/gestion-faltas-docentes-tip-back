package ar.edu.unq.tpi.ciriaqui.exception

import ar.edu.unq.tpi.ciriaqui.dto.LackDTO

class DuplicateLackInDateException(aLackDTO : LackDTO) : RuntimeException("The teacher with ID: ${aLackDTO.idTeacher} already have a lack ")
