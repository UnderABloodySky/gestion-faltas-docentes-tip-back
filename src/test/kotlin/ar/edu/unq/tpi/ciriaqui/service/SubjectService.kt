package ar.edu.unq.tpi.ciriaqui.service

import ar.edu.unq.tpi.ciriaqui.dao.SubjectRepository
import ar.edu.unq.tpi.ciriaqui.dto.SubjectDTO
import ar.edu.unq.tpi.ciriaqui.exception.BadNameException
import ar.edu.unq.tpi.ciriaqui.exception.DuplicateLackInDateException
import ar.edu.unq.tpi.ciriaqui.exception.LackNotFoundException
import ar.edu.unq.tpi.ciriaqui.exception.SubjectFoundException
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Cycle
import ar.edu.unq.tpi.ciriaqui.model.Subject
import ar.edu.unq.tpi.ciriaqui.utils.LackValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubjectService(@Autowired var subjectRepository : SubjectRepository) {
    fun save(aSubjectDTO: SubjectDTO): Subject {
        if(aSubjectDTO.name == ""){
            throw BadNameException()
        }
        val name = aSubjectDTO.name
        val cycle = Cycle.valueOf(aSubjectDTO.cycle)
        return subjectRepository.save(Subject(name, cycle))
    }

    fun findSubjectById(aId: Long): Subject? {
        val foundSubject = subjectRepository.findById(aId)
        return if(foundSubject.isPresent) foundSubject.get() else throw SubjectFoundException(aId)
    }
}
