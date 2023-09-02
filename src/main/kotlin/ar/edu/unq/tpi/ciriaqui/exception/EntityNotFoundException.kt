package ar.edu.unq.tpi.ciriaqui.exception

open class EntityNotFoundException(anEntity : String, id: Any?) : RuntimeException("$anEntity with ID $id not found")