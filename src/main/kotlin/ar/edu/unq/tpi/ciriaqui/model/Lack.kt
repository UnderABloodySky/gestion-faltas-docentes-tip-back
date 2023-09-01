package ar.edu.unq.tpi.ciriaqui.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Lack {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    val article: Article

    val beginDate: LocalDate

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    val teacher: Teacher?

    constructor(article: Article, beginDate: LocalDate, teacher: Teacher?) {
        this.article = article
        this.beginDate = beginDate
        this.teacher = teacher
    }

    constructor() : this(article = Article.PARTICULAR, beginDate = LocalDate.now(), teacher = null)
}

