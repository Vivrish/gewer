package cz.cvut.fit.ejk.server.service.crud

import cz.cvut.fit.ejk.server.common.getFileDto
import cz.cvut.fit.ejk.server.domain.model.FileMetadata
import cz.cvut.fit.ejk.server.domain.table.FilesMetaData
import cz.cvut.fit.ejk.server.domain.table.UsersFilesMetadata
import cz.cvut.fit.ejk.shared.dto.CreateFileDto
import cz.cvut.fit.ejk.shared.dto.GetFileDto
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class FileMetadataService: CrudService<Int, FileMetadata, CreateFileDto, CreateFileDto, GetFileDto>(), KoinComponent {
    override val repository: EntityClass<Int, FileMetadata> by inject(named("fileRepo"))
    override fun createFromDto(createDto: CreateFileDto): FileMetadata {
        return FileMetadata.new {
            filename = createDto.filename
            fileType = createDto.fileType
            size = createDto.size
            path = createDto.path
        }
    }


    override fun updateFromDto(
        entity: FileMetadata,
        updateDto: CreateFileDto
    ) {
        entity.filename = updateDto.filename
        entity.fileType = updateDto.fileType
        entity.size = updateDto.size
        entity.path = updateDto.path
    }

    override fun getDto(entity: FileMetadata): GetFileDto {
        return getFileDto(entity)
    }

    override fun delete(id: Int) {
        transaction {
            val fileMetadata = repository.findById(id)
            if (fileMetadata == null) {
                return@transaction
            }
            UsersFilesMetadata.deleteWhere { UsersFilesMetadata.fileId eq id }
            fileMetadata.delete()
        }
    }

}