package cz.cvut.fit.ejk.shared.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(val username: String, val email: String)
