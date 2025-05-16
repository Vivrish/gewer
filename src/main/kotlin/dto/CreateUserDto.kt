package cz.cvut.fit.ejk.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(val username: String, val email: String)
