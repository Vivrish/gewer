package cz.cvut.fit.ejk.shared.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetUserDto(val id: Int, val username: String, val email: String, val files: List<String>) {
}
