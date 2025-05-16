package cz.cvut.fit.ejk.service

import cz.cvut.fit.ejk.domain.model.FileMetadata
import cz.cvut.fit.ejk.domain.model.User
import cz.cvut.fit.ejk.dto.CreateUserDto
import cz.cvut.fit.ejk.dto.GetFileDto
import cz.cvut.fit.ejk.dto.GetUserDto
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction


class UserService(): CrudService<Int, User, CreateUserDto, CreateUserDto, GetUserDto>(User) {
    override fun createFromDto(createDto: CreateUserDto): User {
        return User.new {
            username = createDto.username
            email = createDto.email
        }
    }

    override fun updateFromDto(
        entity: User,
        updateDto: CreateUserDto
    ) {
        entity.username = updateDto.username
        entity.email = updateDto.email
    }

    override fun getDto(entity: User): GetUserDto {
        return GetUserDto(entity)
    }

    fun getFiles(id: Int): List<GetFileDto> {
        return transaction {
            User.findById(id)?.files?.map { GetFileDto(it) } ?: emptyList()
        }
    }
    fun addFile(id: Int, fileId: Int): GetUserDto? {
        return transaction {
            val user = User.findById(id) ?: return@transaction null
            val file = FileMetadata.findById(fileId) ?: return@transaction null
            user.files = SizedCollection(user.files.plusElement(file))
            getDto(user)
        }
    }
    fun removeFile(id: Int, fileId: Int): GetUserDto? {
        return transaction {
            val user = User.findById(id) ?: return@transaction null
            val file = FileMetadata.findById(fileId) ?: return@transaction null
            user.files = SizedCollection(user.files.minusElement(file))
            getDto(user)
        }
    }


}