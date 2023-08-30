package ar.edu.unq.tpi.ciriaqui

class TeacherNotFoundException(anIncorrectTeacherID: String) : RuntimeException("Teacher with ID $anIncorrectTeacherID not found")