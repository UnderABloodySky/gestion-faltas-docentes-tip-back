package ar.edu.unq.tpi.ciriaqui.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
data class Teacher(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name : String,
    @Column(unique = true)
    val email : String,
    var password: String){

    fun isCorrectPassword(password: String): Boolean = this.password == password

    constructor() : this(null, "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Teacher

        return id != null && id == other.id
    }

    @Override
    override fun toString(): String = this::class.simpleName + "(id = $id )"
}

