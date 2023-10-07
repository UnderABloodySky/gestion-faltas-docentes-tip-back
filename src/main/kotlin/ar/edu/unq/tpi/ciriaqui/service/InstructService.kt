package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.dao.InstructRepository
import ar.edu.unq.tpi.ciriaqui.exception.InstructNotFoundException
import ar.edu.unq.tpi.ciriaqui.model.Instruct
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors


@Service
class InstructService(@Autowired var instructRepository: InstructRepository, @Autowired lackService : LackService) {

    fun findById(id : Long) : Instruct {
        val foundInstruct = instructRepository.findById(id)
        return if (foundInstruct.isPresent) foundInstruct.get() else throw InstructNotFoundException(id)
    }

    fun findTeachersBySubjectId(subjectId: Long): List<Long> {
        val instructs: List<Instruct> = instructRepository.findBySubjectId(subjectId)

        val ids = instructs.stream()
            .map<Any>(Instruct::getTeacherId)
            .collect(Collectors.toList())

        return ids as List<Long>;
    }

}