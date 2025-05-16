package cz.cvut.fit.ejk.di

import cz.cvut.fit.ejk.domain.model.FileMetadata
import cz.cvut.fit.ejk.service.FileService
import cz.cvut.fit.ejk.service.UserService
import org.jetbrains.exposed.dao.EntityClass
import org.koin.dsl.module

val appModule = module {
    single<UserService> { UserService( ) }
    single<FileService> { FileService( ) }
}