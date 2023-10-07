package ar.edu.unq.tpi.ciriaqui.dao


import ar.edu.unq.tpi.ciriaqui.model.Instruct
import org.springframework.data.jpa.repository.JpaRepository

interface InstructRepository : JpaRepository<Instruct, Long>{}
