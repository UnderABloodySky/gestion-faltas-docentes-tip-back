package ar.edu.unq.tpi.ciriaqui.dao

import ar.edu.unq.tpi.ciriaqui.model.Lack
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface LackRepository  : JpaRepository<Lack, Long>