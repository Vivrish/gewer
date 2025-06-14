package cz.cvut.fit.ejk.server.domain.table


import org.jetbrains.exposed.sql.Table

object UsersFilesMetadata: Table("users_files_metadata") {
    val userId = reference("user_id", Users.id)
    val fileId = reference("file_id", FilesMetaData.id)
    override val primaryKey = PrimaryKey(
        userId,
        fileId
    )
}