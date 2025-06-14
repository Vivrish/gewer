package cz.cvut.fit.ejk.server.service.crud

import cz.cvut.fit.ejk.server.common.getFileDto
import cz.cvut.fit.ejk.server.common.getUserDto
import cz.cvut.fit.ejk.server.domain.model.FileMetadata
import cz.cvut.fit.ejk.server.domain.model.User
import cz.cvut.fit.ejk.shared.dto.CreateUserDto
import cz.cvut.fit.ejk.shared.dto.GetFileDto
import cz.cvut.fit.ejk.shared.dto.GetUserDto
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named


class UserService: CrudService<Int, User, CreateUserDto, CreateUserDto, GetUserDto>(), KoinComponent {
    override val repository: EntityClass<Int, User> by inject(named("userRepo"))
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
        return getUserDto(entity)
    }

    fun getFiles(id: Int): List<GetFileDto> {
        return transaction {
            User.findById(id)?.files?.map { getFileDto(it) } ?: emptyList()
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
            file.users = SizedCollection(file.users.minusElement(user))
            getDto(user)
        }
    }


}