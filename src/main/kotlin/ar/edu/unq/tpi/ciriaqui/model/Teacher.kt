package ar.edu.unq.tpi.ciriaqui.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
data class Teacher(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name : String,
    val email : String,
    var password: String){

    fun isCorrectPassword(password: String): Boolean {
        return this.password == password
    }

    constructor() : this(null, "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Teacher

        return id != null && id == other.id
    }

    override fun hashCode(): Int = this.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}

