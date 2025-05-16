package cz.cvut.fit.ejk.domain.table

import org.jetbrains.exposed.dao.id.IntIdTable


object FilesMetaData: IntIdTable("files_metadata") {
    val filename = varchar("filename", 255).uniqueIndex()
    val path = varchar("path", 255).uniqueIndex()
    val fileType = enumerationByName<FileType>("file_type", 255)
    val size = long("size")
}

enum class FileType {
    IMAGE,
    VIDEO,
    TEXT,
    EXECUTABLE,
    UNKNOWN
}