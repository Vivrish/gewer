package cz.cvut.fit.ejk.server.di

import cz.cvut.fit.ejk.server.domain.model.FileMetadata
import cz.cvut.fit.ejk.server.domain.model.User
import cz.cvut.fit.ejk.server.service.crud.FileMetadataService
import cz.cvut.fit.ejk.server.service.file.MinioService
import cz.cvut.fit.ejk.server.service.crud.UserService
import cz.cvut.fit.ejk.server.service.file.FileService
import org.jetbrains.exposed.dao.EntityClass
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<UserService> { UserService( ) }
    single<FileMetadataService> { FileMetadataService( ) }
    single<FileService> { MinioService( ) }
    single<EntityClass<Int, User>>(named("userRepo")) { User }
    single<EntityClass<Int, FileMetadata>>(named("fileRepo")) { FileMetadata }
}