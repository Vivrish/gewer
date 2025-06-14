package cz.cvut.fit.ejk.shared.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateFileDto(val filename: String, val path: String, val fileType: String, val size: Long)
