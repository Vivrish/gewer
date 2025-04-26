package cz.cvut.fit.ejk.domain

import org.jetbrains.exposed.sql.Table

object Users: Table(){
    val id = uuid("id").autoGenerate()
    override val primaryKey = PrimaryKey(
        id
    )
    val username = varchar("username", 255).uniqueIndex()
    val email = varchar("email" ,255).uniqueIndex()
}
