package cz.cvut.fit.ejk.server.common

import cz.cvut.fit.ejk.server.domain.model.FileMetadata
import cz.cvut.fit.ejk.server.domain.model.User
import cz.cvut.fit.ejk.shared.dto.GetFileDto
import cz.cvut.fit.ejk.shared.dto.GetUserDto

fun getFileDto(fileMetadata: FileMetadata): GetFileDto {
    return GetFileDto(
        fileMetadata.id.value,
        fileMetadata.path,
        fileMetadata.filename,
        fileMetadata.size,
        fileMetadata.fileType,
        fileMetadata.users.map { it.username })
}
fun getUserDto(user: User): GetUserDto {
    return GetUserDto(user.id.value, user.username, user.email, user.files.map { it.filename })
}
