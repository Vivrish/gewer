package cz.cvut.fit.ejk.server.domain.table

import org.jetbrains.exposed.dao.id.IntIdTable


object FilesMetaData: IntIdTable("files_metadata") {
    val filename = varchar("filename", 255).uniqueIndex()
    val path = varchar("path", 255).uniqueIndex()
    val fileType = varchar("file_type", 255)
    val size = long("size")
}
