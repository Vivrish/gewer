package cz.cvut.fit.ejk.server.domain.model

import cz.cvut.fit.ejk.server.domain.table.Users
import cz.cvut.fit.ejk.server.domain.table.UsersFilesMetadata
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID



class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var email by Users.email

    var files by FileMetadata via UsersFilesMetadata
}
