package cz.cvut.fit.ejk.dto

import cz.cvut.fit.ejk.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class GetUserDto(val id: Int, val username: String, val email: String, val files: List<String>) {
    constructor(user: User) : this(user.id.value, user.username, user.email, user.files.map { it.filename })
}
