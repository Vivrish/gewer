package cz.cvut.fit.ejk.dto

import cz.cvut.fit.ejk.domain.model.FileMetadata
import kotlinx.serialization.Serializable

@Serializable
data class GetFileDto(val id: Int, val path: String, val filename: String, val users: List<String>){
    constructor(fileMetadata: FileMetadata) : this(fileMetadata.id.value, fileMetadata.path, fileMetadata.filename, fileMetadata.users.map { it.username })
}
