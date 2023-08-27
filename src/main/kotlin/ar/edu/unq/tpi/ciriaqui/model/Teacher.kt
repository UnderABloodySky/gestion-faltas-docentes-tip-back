package ar.edu.unq.tpi.ciriaqui.model

import jakarta.persistence.*

@Entity
@Table(name = "Teacher")
data class Teacher(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String) {

    protected constructor() : this(null, "")
}

