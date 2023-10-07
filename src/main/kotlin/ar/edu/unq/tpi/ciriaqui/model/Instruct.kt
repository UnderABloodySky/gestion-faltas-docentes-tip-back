package ar.edu.unq.tpi.ciriaqui.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Instruct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    var subject: Subject? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher? = null

    var since: LocalDate

    constructor(subject: Subject?, teacher: Teacher?, aDate: LocalDate = LocalDate.now()) {
        this.subject = subject
        this.teacher = teacher
        this.since = aDate
    }

    constructor() : this(null, null, LocalDate.now())
}

