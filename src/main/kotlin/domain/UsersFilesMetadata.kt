package cz.cvut.fit.ejk.domain

import org.jetbrains.exposed.sql.Table

object UsersFilesMetadata: Table() {
    val userId = reference("user_id", Users.id)
    val fileId = reference("file_id", FilesMetaData.id)
    override val primaryKey = PrimaryKey(
        userId,
        fileId
    )
}