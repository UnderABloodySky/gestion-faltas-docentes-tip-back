package ar.edu.unq.tpi.ciriaqui.model

import jakarta.persistence.*
@Entity
class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val name : String
    @Enumerated(EnumType.STRING)
    var cycle : Cycle

    constructor(aName : String, aCycle : Cycle){
        name = aName
        cycle = aCycle
    }

    constructor() : this("", Cycle.SECUNDARY)
}


