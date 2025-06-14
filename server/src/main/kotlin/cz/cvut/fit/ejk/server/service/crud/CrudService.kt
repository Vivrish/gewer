package cz.cvut.fit.ejk.server.service.crud

import cz.cvut.fit.ejk.server.service.file.FileService
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.inject
import org.slf4j.LoggerFactory


abstract class CrudService<ID : Comparable<ID>, T: Entity<ID>, TCreateDto, TUpdateDto, TGetDto> {
    private val logger = LoggerFactory.getLogger(this::class.java)
    protected abstract fun createFromDto(createDto: TCreateDto): T
    protected abstract fun updateFromDto(entity: T, updateDto: TUpdateDto): Unit
    protected abstract fun getDto(entity: T): TGetDto
    protected abstract val repository: EntityClass<ID, T>


    open fun getById(id: ID): TGetDto? {
        logger.info("Getting ${repository.table.tableName} with id=$id...")
        return transaction {
            val entity = repository.findById(id)
            if (entity != null) getDto(entity) else null
        }
    }

    open fun getAll(): List<TGetDto> {
        logger.info("Getting all ${repository.table.tableName}...")
        return transaction {
            repository.all().toList().map { getDto(it) }
        }
    }
    open fun create(dto: TCreateDto): TGetDto {
        logger.info("Creating ${repository.table.tableName}...")
        return transaction {
            getDto(createFromDto(dto))
        }
    }
    open fun update(id: ID, updateDto: TUpdateDto): TGetDto? {
        logger.info("Updating ${repository.table.tableName} with id=$id...")
        return transaction {
            val entityToUpdate = repository.findById(id)
            if (entityToUpdate == null) {
                return@transaction null
            }
            updateFromDto( entityToUpdate, updateDto)
            getDto(entityToUpdate)
        }
    }
    open fun delete(id: ID) {
        logger.info("Deleting ${repository.table.tableName} with id=$id...")

        transaction {
            repository.findById(id)?.delete()
        }
    }
    open fun exists(id: ID): Boolean {
        logger.info("Checking if ${repository.table.tableName} with id=$id exists...")
        return transaction {
            repository.findById(id) != null
        }
    }
}