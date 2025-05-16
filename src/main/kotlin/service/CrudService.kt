package cz.cvut.fit.ejk.service

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass


abstract class CrudService<ID : Comparable<ID>, T: Entity<ID>>(
    protected val repository: EntityClass<ID, T>) {

    fun getById(id: ID): T? {
        return repository.findById(id)
    }
    fun getAll(): List<T> {
        return repository.all().toList()
    }
    fun create(entity: T): T {
        return repository.new { entity }
    }
    fun update(id: ID, block: (T) -> Unit): T? {
        return repository.findByIdAndUpdate(id, block)
    }
    fun delete(id: ID) {
        repository.findById(id)?.delete()
    }
    fun exists(id: ID): Boolean {
        return repository.findById(id) != null
    }
}