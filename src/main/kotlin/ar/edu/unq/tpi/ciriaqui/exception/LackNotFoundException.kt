package ar.edu.unq.tpi.ciriaqui.exception

class LackNotFoundException(id: Long?) : RuntimeException("Lack with ID $id not found") {

}
