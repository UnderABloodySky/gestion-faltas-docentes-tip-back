package ar.edu.unq.tpi.ciriaqui

import ar.edu.unq.tpi.ciriaqui.exception.EntityNotFoundException

class TeacherNotFoundException(anIncorrectTeacherID: String) : EntityNotFoundException("Teacher", anIncorrectTeacherID)