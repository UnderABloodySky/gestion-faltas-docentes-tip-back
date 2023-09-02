package ar.edu.unq.tpi.ciriaqui.exception

class LackNotFoundException(id: Long?) : EntityNotFoundException("Lack", id)
