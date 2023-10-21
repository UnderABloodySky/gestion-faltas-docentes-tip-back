package ar.edu.unq.tpi.ciriaqui.model

import jakarta.persistence.*

@Entity
class Student{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    var course: Course

    @Column(unique = true)
    var record : Int? = null

    var name: String

    constructor(aName : String, aRecord: Int?, aCourse : Course){
        name = aName
        course = aCourse
        record = aRecord
    }
    constructor() : this("", null, Course.FIRST)
}





