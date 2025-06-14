package cz.cvut.fit.ejk.server.domain.table

import org.jetbrains.exposed.dao.id.IntIdTable


object Users: IntIdTable("users"){
    val username = varchar("username", 255).uniqueIndex()
    val email = varchar("email" ,255).uniqueIndex()
}