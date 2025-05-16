package cz.cvut.fit.ejk.domain.model

import cz.cvut.fit.ejk.domain.table.FilesMetaData
import cz.cvut.fit.ejk.domain.table.UsersFilesMetadata
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID


class FileMetadata(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<FileMetadata>(FilesMetaData)

    var filename by FilesMetaData.filename
    var path by FilesMetaData.path
    var fileType by FilesMetaData.fileType
    var size by FilesMetaData.size

    var users by User via UsersFilesMetadata
}
