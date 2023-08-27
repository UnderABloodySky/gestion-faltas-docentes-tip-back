package ar.edu.unq.tpi.ciriaqui

class TeacherNotFoundException(anIncorrectTeacherID : Long) : RuntimeException("Teacher with ID $anIncorrectTeacherID not found")