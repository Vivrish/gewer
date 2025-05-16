package cz.cvut.fit.ejk.service

import cz.cvut.fit.ejk.domain.model.FileMetadata
import cz.cvut.fit.ejk.domain.table.FileType
import cz.cvut.fit.ejk.dto.CreateFileDto
import cz.cvut.fit.ejk.dto.GetFileDto

class FileService(): CrudService<Int, FileMetadata, CreateFileDto, CreateFileDto, GetFileDto>(FileMetadata) {
    override fun createFromDto(createDto: CreateFileDto): FileMetadata {
        return FileMetadata.new {
            filename = createDto.filename
            fileType = FileType.valueOf(createDto.fileType)
            size = createDto.size
            path = createDto.path
        }
    }

    override fun updateFromDto(
        entity: FileMetadata,
        updateDto: CreateFileDto
    ) {
        entity.filename = updateDto.filename
        entity.fileType = FileType.valueOf(updateDto.fileType)
        entity.size = updateDto.size
        entity.path = updateDto.path
    }

    override fun getDto(entity: FileMetadata): GetFileDto {
        return GetFileDto(entity)
    }


}