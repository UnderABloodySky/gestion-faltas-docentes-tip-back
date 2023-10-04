package ar.edu.unq.tpi.ciriaqui.exception

class SubjectFoundException(id: Long) : RuntimeException("Subject with ${id} doesn't exist") {

}
