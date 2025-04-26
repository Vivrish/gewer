package cz.cvut.fit.ejk.domain

import org.jetbrains.exposed.sql.Table

object FilesMetaData: Table() {
    val id = uuid("id").autoGenerate()
    override val primaryKey = PrimaryKey(
        id
    )
    val filename = varchar("filename", 255).uniqueIndex()
    val path = varchar("path", 255).uniqueIndex()
    val fileType = enumerationByName<FileType>("file_type", 255)
    val size = long("size")

    val createdBy = reference("created_by", Users.id)
}

enum class FileType {
    IMAGE,
    VIDEO,
    TEXT,
    EXECUTABLE,
    UNKNOWN
}