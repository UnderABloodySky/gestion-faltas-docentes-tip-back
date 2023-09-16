package ar.edu.unq.tpi.ciriaqui.model

import ar.edu.unq.tpi.ciriaqui.exception.IncorrectDateException
import jakarta.persistence.*
import java.time.DayOfWeek
import java.time.LocalDate

@Entity
class Lack {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    lateinit var article: Article

    val beginDate: LocalDate
    val endDate: LocalDate

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    val teacher: Teacher?

    constructor(article: Article = Article.PARTICULAR, beginDate: LocalDate, endDate: LocalDate, teacher: Teacher?) {
        if(beginDate > endDate){
            throw IncorrectDateException()
        }
        this.article = article
        this.beginDate = beginDate
        this.endDate = endDate
        this.teacher = teacher
    }

    constructor() : this(Article.PARTICULAR, LocalDate.now(), LocalDate.now(), null)

}

