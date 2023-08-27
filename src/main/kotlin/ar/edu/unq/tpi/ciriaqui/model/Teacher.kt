package ar.edu.unq.tpi.ciriaqui.model

import jakarta.persistence.*

@Entity
@Table(name = "TEACHERS")
data class Teacher(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String) {

    protected constructor() : this(null, "")
}

